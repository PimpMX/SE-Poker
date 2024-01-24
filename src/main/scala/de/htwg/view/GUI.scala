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

class GUI(controller: ControllerInterface) extends Frame with Observer {

    val injector = Guice.createInjector(new TexasHoldEmModule)
    val fileIO = injector.getInstance(classOf[FileIOInterface])

    controller.add(this)

    title = "SE-Poker"
    visible = true
    preferredSize = new Dimension(800, 512)
    minimumSize = new Dimension(200, 400)

    val betInputField = new TextField {
        columns = 5
        preferredSize = new Dimension(50, 27)
    }

    val errorOutputField = new TextField {
        preferredSize = new Dimension(200, 27)
        editable = false
    }

    val gamePanel = new GamePanel(controller)

    def update(event: Event): Unit = {
        gamePanel.repaint()
    }

    def inputPanel = new FlowPanel {

        contents += new Label("Amount: ")
        contents += betInputField
        
        contents += Button("Bet") {
            errorOutputField.foreground = Color.red
            controller.bet(betInputField.text.toInt)
        }
        
        contents += Button("All-In") {
            controller.betAllIn()
        }

        contents += Button("Check") {
            controller.check()
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
}

class PlayerRenderable(player: PlayerInterface, gameState: GameFieldInterface, imageHandler: CardImages) {
    
    def getPlayer: PlayerInterface = player

    def draw(g: Graphics2D, font: Font, x: Int, y: Int, atTurn: Boolean, isBottomRenderable: Boolean): Unit = {
        
        g.setFont(font)

        if(atTurn)
            g.setColor(new Color(255, 64, 0))
        else
            g.setColor(Color.WHITE)
            
        g.drawString(if(!player.getFoldedStatus) player.getPlayerStr else "FOLDED", x, y)
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

    //  Our preferred font
    val usedFont = new Font("Arial", Font.BOLD, 17)

    //  Handles our card images
    val imageHandler: CardImages = new CardImages

    override def paintComponent(g: Graphics2D): Unit = {
        
        super.paintComponent(g)

        //  Enable Anti-Aliasing

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        //  Draw Background first

        g.drawImage(imageHandler.getTableBackground, 0, 0, null)
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //  Draw Players

        val players = controller.getGameState().getPlayers
        val renderables = players.map(new PlayerRenderable(_, controller.getGameState(), imageHandler))
        val (topRenderables, botRenderables) = renderables.splitAt((renderables.length / 2))
        val currentPlayer = controller.getGameState().getPlayerAtTurn.getPlayerNum

        for(i <- 0 until topRenderables.length) {
            topRenderables(i).draw(g, usedFont, 40 + (i * 300), 40,
                topRenderables(i).getPlayer.getPlayerNum == currentPlayer, false)
        }

        for(i <- 0 until botRenderables.length) {
            botRenderables.reverse(i).draw(g, usedFont, 40 + (i * 300), size.height - 225,
                botRenderables.reverse(i).getPlayer.getPlayerNum == currentPlayer, true)
        }

        //  Draw Community Cards

        val comCards = controller.getGameState().getCommunityCards

        for(i <- 0 until comCards.getCards.length) {

            if(comCards.getCards(i).isRevealed || controller.getGameState().getBettingRound == SHOWDOWN) {
                val renderedCard = cardFactory(comCards.getCards(i).getColor, comCards.getCards(i).getRank)
                g.drawImage(imageHandler.cardImages(renderedCard), size.width / 2 - 200 + (i * 110), size.height / 2 - 75, null)
            } else {
                g.drawImage(imageHandler.cardBackside, size.width / 2 - 200 + (i * 110), size.height / 2 - 75, null)
            }
        }

        g.drawImage(imageHandler.pokerChipsImage, size.width / 2 - 550, size.height / 2 - 130, null)
        g.setColor(Color.BLUE)
        g.drawString(f"Current Pot: ${controller.getGameState().getMoneyInPool}", size.width / 2 - 520, size.height / 2 + 125)
    }
}
