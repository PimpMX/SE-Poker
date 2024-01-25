package de.htwg.view

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.controller.ControllerInterface
import de.htwg.util.Observer
import de.htwg.util.Event
import java.awt.Color
import java.awt.Font

import de.htwg.model.gameFieldComponent._
import javax.smartcardio.Card
import com.google.inject.Guice
import de.htwg.TexasHoldEmModule
import de.htwg.model.fileIoComponent.FileIOInterface
import de.htwg.util.Move
import java.awt.RenderingHints
import javax.swing.SwingConstants
import swing.Alignment.Value

class GUI(controller: ControllerInterface) extends Frame with Observer {

    val injector = Guice.createInjector(new TexasHoldEmModule)
    val fileIO = injector.getInstance(classOf[FileIOInterface])

    controller.add(this)

    title = "SE-Poker"
    preferredSize = new Dimension(1600, 1080)
    minimumSize = new Dimension(1000, 1000)

    val betInputField = new TextField {
        columns = 5
        preferredSize = new Dimension(50, 27)
    }

    val errorOutputField = new TextField {
        preferredSize = new Dimension(1200, 40)
        font = new Font("Arial", Font.BOLD, 30) // Set a larger font
        horizontalAlignment = Alignment.Center // Center the text horizontally
    }

    val gamePanel = new GamePanel(controller)

    def update(event: Event): Unit = {
        gamePanel.repaint()
    }

    def inputPanel = new FlowPanel {

        contents += new Label("Amount: ")
        contents += betInputField
        
        contents += Button("Bet") {

            val executed = controller.bet(betInputField.text.toInt)
            
            if(executed) {
                errorOutputField.foreground = Color.red
                errorOutputField.text = ""
            } else {
                errorOutputField.foreground = Color.red
                errorOutputField.text = "You don't have enough money left or are betting to little!"
            }
        }

        contents += Button("All-In") {

            val executed = controller.betAllIn()

            if(executed) {
                errorOutputField.foreground = Color.red
                errorOutputField.text = ""
            }
        }

        contents += Button("Call") {
            
            val executed = controller.call()

            if(executed) {
                errorOutputField.foreground = Color.red
                errorOutputField.text = ""
            } else {
                errorOutputField.foreground = Color.red
                errorOutputField.text = "You have enough money in the pot or dont have enough money to call!"
            }
        }

        contents += Button("Check") {

            val executed = controller.check()

            if(executed) {
                errorOutputField.foreground = Color.red
                errorOutputField.text = ""
            } else {
                errorOutputField.foreground = Color.red
                errorOutputField.text = "You cannot check right now, you have to bet more money!"
            }
        }

        contents += Button("Fold") {
            controller.fold()
        }
    }

    def errorPanel = new FlowPanel {
        contents += errorOutputField
    }

    contents = new BorderPanel {
        add(inputPanel, BorderPanel.Position.North)
        add(errorPanel, BorderPanel.Position.South)
        add(gamePanel, BorderPanel.Position.Center)
    }

    menuBar = new MenuBar {

        contents += new Menu("Game") {
            mnemonic = Key.F
            contents += new MenuItem(Action("New") { newGameDialog() })
            contents += new MenuItem(Action("Quit") { controller.exit() })

            contents += new MenuItem(Action("Load") {
                controller.setGameState(fileIO.load)
                controller.notifyObservers(Move)
            })
            
            contents += new MenuItem(Action("Save") {
                fileIO.save(controller.getGameState())
                controller.notifyObservers(Move)
            })
        }

        contents += new Menu("Edit") {
            mnemonic = Key.E
                contents += new MenuItem(Action("Undo") { controller.undo() })
                contents += new MenuItem(Action("Redo") { controller.redo() })
        }
    }

    def newGameDialog(): Unit = {

        val input = Dialog.showInput[String](null,
            "Anzahl Spieler:",
            "Neues Spiel",
            Dialog.Message.Question,
            Swing.EmptyIcon, Nil, "0")

        if(input.isDefined) {
            controller.newGame(input.get.toInt)
        }
    }

    pack()  
    visible = true
}

class PlayerRenderable(player: PlayerInterface, gameState: GameFieldInterface, imageHandler: CardImages) {
    
    def getPlayer: PlayerInterface = player

    def draw(g: Graphics2D, eval: Option[Vector[CardEvaluation]], font: Font, x: Int, y: Int, atTurn: Boolean, isBottomRenderable: Boolean): Unit = {
        
        g.setFont(font)

        if(atTurn)
            g.setColor(new Color(255, 64, 0))
        else
            g.setColor(Color.WHITE)

        //  Depending on if the player has folded and which round it is 
        //  We render different strings. Round != Showdown just produces the playername
        //  and Round == Showdown and Player not Folded additionally shows the hand ranking
        //  at last if the player is folded we only show "FOLDED"

        val displayString = if(!player.getFoldedStatus && gameState.getBettingRound != SHOWDOWN) {
            player.getPlayerStr
        } else if(!player.getFoldedStatus && gameState.getBettingRound == SHOWDOWN && eval.isDefined) {
            
            val playerEval = eval.get.filter(e => e.player.getPlayerNum == player.getPlayerNum)
            
            if(playerEval.headOption.isDefined) {

                player.getPlayerStr + " (" + (playerEval.head.handRank match {
                    case ROYAL_FLUSH => "Royal Flush"
                    case STRAIGHT_FLUSH => "Straight Flush"
                    case FOUR_OF_A_KIND => "Four of a Kind"
                    case FULL_HOUSE => "Full House"
                    case FLUSH => "Flush"
                    case STRAIGHT => "Straight"
                    case THREE_OF_A_KIND => "Three of a Kind"
                    case TWO_PAIR => "Two Pair"
                    case ONE_PAIR => "One Pair"
                    case HIGH_CARD => "High Card"
                }) + ")"

            } else player.getPlayerStr
        } else {
            "FOLDED"
        }

        g.drawString(displayString, x, y)
        g.drawString(f"Balance: ${player.getBalanceStr}", x, y + 175)
        g.drawString(f"Current Bet: ${player.getBettedStr}", x, y + 195)

        val smallBlindIdx  = if(gameState.getBigBlindPlayerIdx - 1 < 0)
                gameState.getBigBlindPlayerIdx - 1 + gameState.getPlayers.length
            else 
                gameState.getBigBlindPlayerIdx - 1

        if(player.getPlayerNum == gameState.getBigBlindPlayerIdx) {

            if(!isBottomRenderable)
                g.drawImage(imageHandler.bigBlindImage, x + 30, y + 210, null)
            else
                g.drawImage(imageHandler.bigBlindImage, x + 30, y - 100, null)

        
        } else if(player.getPlayerNum == smallBlindIdx) {

            if(!isBottomRenderable)
                g.drawImage(imageHandler.smallBlindImage, x + 30, y + 210, null)
            else
                g.drawImage(imageHandler.smallBlindImage, x + 30, y - 100, null)
                
        }

        //  Only reveal the cards of the player if hes at turn OR
        //  if the game is in showdown AND the player hasn't folded
        //  since it is a strategic decision to show your cards or not

        if(atTurn && gameState.getBettingRound != SHOWDOWN ||
            (gameState.getBettingRound == SHOWDOWN && player.getFoldedStatus == false)) {
            g.drawImage(imageHandler.cardImages(player.getHand.cards._1), x, y + 10, null)
            g.drawImage(imageHandler.cardImages(player.getHand.cards._2), x + 110, y + 10, null)
        } else {
            g.drawImage(imageHandler.cardBackside, x, y + 10, null)
            g.drawImage(imageHandler.cardBackside, x + 110, y + 10, null)
        }
    }
}

class GamePanel(controller: ControllerInterface) extends Panel {

    val injector = Guice.createInjector(new TexasHoldEmModule)
    val cardFactory = injector.getInstance(classOf[CardFactoryInterface])
    val cardEvaluator = injector.getInstance(classOf[CardEvaluatorInterface])

    //  Our preferred font
    val usedFont = new Font("Arial", Font.BOLD, 17)
    val secondFont = new Font("Arial", Font.BOLD, 25)

    //  Handles our card images
    val imageHandler: CardImages = new CardImages

    override def paintComponent(g: Graphics2D): Unit = {

        super.paintComponent(g)

        //  Enable Anti-Aliasing

        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        //  Draw Background first

        g.drawImage(imageHandler.getTableBackground, 0, 0, null)

        //  Get current GameState for easy accessibility

        val gameState = controller.getGameState()

        //  In case we are in SHOWDOWN we want to get the card evaluations to render them

        val cardEvaluation: Option[Vector[CardEvaluation]] = if(gameState.getBettingRound == SHOWDOWN)
                Some(cardEvaluator.rankCards(gameState.getPlayers, gameState.getCommunityCards))
            else 
                Option.empty

        //  Draw Players

        val players = gameState.getPlayers
        val renderables = players.map(new PlayerRenderable(_, gameState, imageHandler))
        val (topRenderables, botRenderables) = renderables.splitAt((renderables.length / 2))
        val currentPlayer = gameState.getPlayerAtTurn.getPlayerNum

        for(i <- 0 until topRenderables.length) {
            topRenderables(i).draw(g, cardEvaluation, usedFont, 40 + (i * 300), 40,
                topRenderables(i).getPlayer.getPlayerNum == currentPlayer, false)
        }

        for(i <- 0 until botRenderables.length) {
            botRenderables.reverse(i).draw(g, cardEvaluation, usedFont, 40 + (i * 300), size.height - 225,
                botRenderables.reverse(i).getPlayer.getPlayerNum == currentPlayer, true)
        }

        //  Draw Community Cards

        val comCards = gameState.getCommunityCards

        for(i <- 0 until comCards.getCards.length) {

            if(comCards.getCards(i).isRevealed || gameState.getBettingRound == SHOWDOWN) {
                val renderedCard = cardFactory(comCards.getCards(i).getColor, comCards.getCards(i).getRank)
                g.drawImage(imageHandler.cardImages(renderedCard), size.width / 2 - 200 + (i * 110), size.height / 2 - 75, null)
            } else {
                g.drawImage(imageHandler.cardBackside, size.width / 2 - 200 + (i * 110), size.height / 2 - 75, null)
            }
        }

        //  Draw Pot Information

        g.drawImage(imageHandler.pokerChipsImage, size.width / 2 - 550, size.height / 2 - 130, null)
        
        g.setFont(usedFont)
        g.setColor(Color.BLUE)
        g.drawString(f"Current Pot: ${gameState.getMoneyInPool}", size.width / 2 - 520, size.height / 2 + 125)
    
        //  Draw round is over alert in case the round is over

        if(gameState.getBettingRound == SHOWDOWN) {

            g.setFont(secondFont)
            g.setColor(Color.RED)
            
            g.drawString("ROUND IS OVER", size.width / 2 - 40, size.height / 2 - 150)
            g.drawString("PRESS ANY BUTTON FOR NEXT ROUND", size.width / 2 - 170, size.height / 2 - 120)
            
            val winPlayer = cardEvaluation

            if(winPlayer.isDefined && winPlayer.get.headOption.isDefined) {

                val winningEval = winPlayer.get.head

                val winMessage = winningEval.player.getPlayerStr + " won the Pot with a " + (winningEval.handRank match {
                    case ROYAL_FLUSH => "Royal Flush"
                    case STRAIGHT_FLUSH => "Straight Flush"
                    case FOUR_OF_A_KIND => "Four of a Kind"
                    case FULL_HOUSE => "Full House"
                    case FLUSH => "Flush"
                    case STRAIGHT => "Straight"
                    case THREE_OF_A_KIND => "Three of a Kind"
                    case TWO_PAIR => "Two Pair"
                    case ONE_PAIR => "One Pair"
                    case HIGH_CARD => "High Card"
                })

                g.setColor(Color.BLUE)
                g.drawString(winMessage, size.width / 2 - 150, size.height / 2 + 120)
            }
        } else if(gameState.getBettingRound == GAME_FINISHED) {

            val winPlayer = gameState.getPlayers.headOption

            if(winPlayer.isDefined) {
                g.setFont(secondFont)
                g.setColor(Color.RED)
                g.drawString("GAME IS OVER", size.width / 2 - 40, size.height / 2 - 150)
                g.drawString(winPlayer.get.getPlayerStr + " HAS WON THE GAME!", size.width / 2 - 130, size.height / 2 - 120)
            }
        }
    }
}
