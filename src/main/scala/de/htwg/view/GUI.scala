package de.htwg.view

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.controller.Controller
import de.htwg.util.Observer
import de.htwg.util.Event
import java.awt.Color
import java.awt.Font
import de.htwg.model.Player
import de.htwg.model.Card

class GUI(controller: Controller) extends Frame with Observer {

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

class PlayerRenderable(player: Player, imageHandler: CardImages) {
    
    def getPlayer: Player = player

    def draw(g: Graphics2D, font: Font, x: Int, y: Int, atTurn: Boolean): Unit = {
        
        g.setFont(font)

        if(atTurn)
            g.setColor(Color.RED)
        else
            g.setColor(Color.BLACK)
            
        g.drawString(if(!player.hasFolded) player.getPlayerStr else "FOLDED", x, y)
        g.drawString(f"Balance: ${player.getBalanceStr}", x, y + 175)
        g.drawString(f"Betted: ${player.getBettedStr}", x, y + 195)

        if(atTurn)
            g.drawImage(imageHandler.cardImages(player.hand.cards._1), x, y + 10, null)
            g.drawImage(imageHandler.cardImages(player.hand.cards._2), x + 110, y + 10, null)
        else
            g.drawImage(imageHandler.cardBackside, x, y + 10, null)
            g.drawImage(imageHandler.cardBackside, x + 110, y + 10, null)
    }
}

class GamePanel(controller: Controller) extends Panel {

    //  Our preferred font
    val usedFont = new Font("Arial", Font.BOLD, 15)

    //  Handles our card images
    val imageHandler: CardImages = new CardImages

    override def paintComponent(g: Graphics2D): Unit = {
        
        super.paintComponent(g)

        //  Draw Background first

        g.setColor(Color.GREEN)
        g.fillRoundRect(10, 10, size.width - 20, size.height - 20, 10, 10)
        g.setColor(Color.BLACK)
        g.drawRoundRect(10, 10, size.width - 20, size.height - 20, 10, 10)

        //  Draw Players

        val players = controller.gameState.players
        val renderables = players.map(new PlayerRenderable(_, imageHandler))
        val (topRenderables, botRenderables) = renderables.splitAt((renderables.length / 2))
        val currentPlayer = controller.gameState.getPlayerAtTurn.playerNum

        for(i <- 0 until topRenderables.length) {
            topRenderables(i).draw(g, usedFont, 40 + (i * 300), 40,
                topRenderables(i).getPlayer.playerNum == currentPlayer)
        }

        for(i <- 0 until botRenderables.length) {
            botRenderables(i).draw(g, usedFont, 40 + (i * 300), size.height - 225,
                botRenderables(i).getPlayer.playerNum == currentPlayer)
        }

        //  Draw Community Cards

        val comCards = controller.gameState.comCards

        for(i <- 0 until comCards.getCards.length) {

            if(comCards.getCards(i).isRevealed) {
                val comCard = comCards.getCards(i)
                val card = new Card(comCard.color, comCard.rank)
                g.drawImage(imageHandler.cardImages(card), size.width / 2 - 200 + (i * 110), size.height / 2 - 75, null)
            } else {
                g.drawImage(imageHandler.cardBackside, size.width / 2 - 200 + (i * 110), size.height / 2 - 75, null)
            }
        }
    }
}
