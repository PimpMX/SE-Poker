package de.htwg.view

import de.htwg.model.gameFieldComponent._
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp

import de.htwg.model.gameFieldComponent.cardBaseImpl.Card
import de.htwg.model.gameFieldComponent.CardInterface
import scala.collection.mutable.Buffer

class CardImages {

    val cardToImagePath: Map[CardInterface, String] = Map(
        new Card(HEARTS, TWO) -> "assets/cards/2_of_hearts.png",
        new Card(HEARTS, THREE) -> "assets/cards/3_of_hearts.png",
        new Card(HEARTS, FOUR) -> "assets/cards/4_of_hearts.png",
        new Card(HEARTS, FIVE) -> "assets/cards/5_of_hearts.png",
        new Card(HEARTS, SIX) -> "assets/cards/6_of_hearts.png",
        new Card(HEARTS, SEVEN) -> "assets/cards/7_of_hearts.png",
        new Card(HEARTS, EIGHT) -> "assets/cards/8_of_hearts.png",
        new Card(HEARTS, NINE) -> "assets/cards/9_of_hearts.png",
        new Card(HEARTS, TEN) -> "assets/cards/10_of_hearts.png",
        new Card(HEARTS, JACK) -> "assets/cards/jack_of_hearts.png",
        new Card(HEARTS, QUEEN) -> "assets/cards/queen_of_hearts.png",
        new Card(HEARTS, KING) -> "assets/cards/king_of_hearts.png",
        new Card(HEARTS, ACE) -> "assets/cards/ace_of_hearts.png",
        new Card(CLUBS, TWO) -> "assets/cards/2_of_clubs.png",
        new Card(CLUBS, THREE) -> "assets/cards/3_of_clubs.png",
        new Card(CLUBS, FOUR) -> "assets/cards/4_of_clubs.png",
        new Card(CLUBS, FIVE) -> "assets/cards/5_of_clubs.png",
        new Card(CLUBS, SIX) -> "assets/cards/6_of_clubs.png",
        new Card(CLUBS, SEVEN) -> "assets/cards/7_of_clubs.png",
        new Card(CLUBS, EIGHT) -> "assets/cards/8_of_clubs.png",
        new Card(CLUBS, NINE) -> "assets/cards/9_of_clubs.png",
        new Card(CLUBS, TEN) -> "assets/cards/10_of_clubs.png",
        new Card(CLUBS, JACK) -> "assets/cards/jack_of_clubs.png",
        new Card(CLUBS, QUEEN) -> "assets/cards/queen_of_clubs.png",
        new Card(CLUBS, KING) -> "assets/cards/king_of_clubs.png",
        new Card(CLUBS, ACE) -> "assets/cards/ace_of_clubs.png",
        new Card(SPADES, TWO) -> "assets/cards/2_of_spades.png",
        new Card(SPADES, THREE) -> "assets/cards/3_of_spades.png",
        new Card(SPADES, FOUR) -> "assets/cards/4_of_spades.png",
        new Card(SPADES, FIVE) -> "assets/cards/5_of_spades.png",
        new Card(SPADES, SIX) -> "assets/cards/6_of_spades.png",
        new Card(SPADES, SEVEN) -> "assets/cards/7_of_spades.png",
        new Card(SPADES, EIGHT) -> "assets/cards/8_of_spades.png",
        new Card(SPADES, NINE) -> "assets/cards/9_of_spades.png",
        new Card(SPADES, TEN) -> "assets/cards/10_of_spades.png",
        new Card(SPADES, JACK) -> "assets/cards/jack_of_spades.png",
        new Card(SPADES, QUEEN) -> "assets/cards/queen_of_spades.png",
        new Card(SPADES, KING) -> "assets/cards/king_of_spades.png",
        new Card(SPADES, ACE) -> "assets/cards/ace_of_spades.png",
        new Card(DIAMONDS, TWO) -> "assets/cards/2_of_diamonds.png",
        new Card(DIAMONDS, THREE) -> "assets/cards/3_of_diamonds.png",
        new Card(DIAMONDS, FOUR) -> "assets/cards/4_of_diamonds.png",
        new Card(DIAMONDS, FIVE) -> "assets/cards/5_of_diamonds.png",
        new Card(DIAMONDS, SIX) -> "assets/cards/6_of_diamonds.png",
        new Card(DIAMONDS, SEVEN) -> "assets/cards/7_of_diamonds.png",
        new Card(DIAMONDS, EIGHT) -> "assets/cards/8_of_diamonds.png",
        new Card(DIAMONDS, NINE) -> "assets/cards/9_of_diamonds.png",
        new Card(DIAMONDS, TEN) -> "assets/cards/10_of_diamonds.png",
        new Card(DIAMONDS, JACK) -> "assets/cards/jack_of_diamonds.png",
        new Card(DIAMONDS, QUEEN) -> "assets/cards/queen_of_diamonds.png",
        new Card(DIAMONDS, KING) -> "assets/cards/king_of_diamonds.png",
        new Card(DIAMONDS, ACE) -> "assets/cards/ace_of_diamonds.png",
    )

    val cardImages: Map[CardInterface, BufferedImage] = loadCardImages
    val cardBackside: BufferedImage = getCardBackside

    val bigBlindImage: BufferedImage = scaleImage(ImageIO.read(new File("assets/gui/big_blind.png")), 0.2)
    val smallBlindImage: BufferedImage = scaleImage(ImageIO.read(new File("assets/gui/small_blind.png")), 0.2)
    val pokerChipsImage: BufferedImage = scaleImage(ImageIO.read(new File("assets/gui/poker_chips.png")), 0.12)
    val tableBackground: BufferedImage = scaleImage(ImageIO.read(new File("assets/gui/table_background.jpg")), 0.8)

    def getBigBlindImage: BufferedImage = bigBlindImage
    def getSmallBlindImage: BufferedImage = smallBlindImage
    def getPokerChipsImage: BufferedImage = pokerChipsImage
    def getTableBackground: BufferedImage = tableBackground

    def loadCardImages: Map[CardInterface, BufferedImage] = {
        cardToImagePath.map {
            case (card, path) =>
            card -> scaleImage(ImageIO.read(new File(path)), 0.20)
        }
    }

    def cardToImage(card: CardInterface): BufferedImage = {
        cardImages(card)
    }

    def getCardBackside: BufferedImage = {
        scaleImage(ImageIO.read(new File("assets/cards/backside.png")), 0.20)
    }

    def scaleImage(originalImage: BufferedImage, scale: Double): BufferedImage = {
        
        val width = originalImage.getWidth
        val height = originalImage.getHeight

        val scaleTransform = new AffineTransform()
        scaleTransform.scale(scale, scale)
        val op = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR)
        val scaledImage = new BufferedImage((width * scale).toInt, (height * scale).toInt, originalImage.getType)
        op.filter(originalImage, scaledImage)

        scaledImage
    }
}
