package de.htwg.view

import de.htwg.model._
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File
import scala.collection.MapView
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp

class CardImages {

    val cardToImagePath: Map[Card, String] = Map(
        Card(PIP, TWO) -> "assets/cards/2_of_hearts.png",
        Card(PIP, THREE) -> "assets/cards/3_of_hearts.png",
        Card(PIP, FOUR) -> "assets/cards/4_of_hearts.png",
        Card(PIP, FIVE) -> "assets/cards/5_of_hearts.png",
        Card(PIP, SIX) -> "assets/cards/6_of_hearts.png",
        Card(PIP, SEVEN) -> "assets/cards/7_of_hearts.png",
        Card(PIP, EIGHT) -> "assets/cards/8_of_hearts.png",
        Card(PIP, NINE) -> "assets/cards/9_of_hearts.png",
        Card(PIP, TEN) -> "assets/cards/10_of_hearts.png",
        Card(PIP, JACK) -> "assets/cards/jack_of_hearts.png",
        Card(PIP, QUEEN) -> "assets/cards/queen_of_hearts.png",
        Card(PIP, KING) -> "assets/cards/king_of_hearts.png",
        Card(PIP, ACE) -> "assets/cards/ace_of_hearts.png",
        Card(CLUBS, TWO) -> "assets/cards/2_of_clubs.png",
        Card(CLUBS, THREE) -> "assets/cards/3_of_clubs.png",
        Card(CLUBS, FOUR) -> "assets/cards/4_of_clubs.png",
        Card(CLUBS, FIVE) -> "assets/cards/5_of_clubs.png",
        Card(CLUBS, SIX) -> "assets/cards/6_of_clubs.png",
        Card(CLUBS, SEVEN) -> "assets/cards/7_of_clubs.png",
        Card(CLUBS, EIGHT) -> "assets/cards/8_of_clubs.png",
        Card(CLUBS, NINE) -> "assets/cards/9_of_clubs.png",
        Card(CLUBS, TEN) -> "assets/cards/10_of_clubs.png",
        Card(CLUBS, JACK) -> "assets/cards/jack_of_clubs.png",
        Card(CLUBS, QUEEN) -> "assets/cards/queen_of_clubs.png",
        Card(CLUBS, KING) -> "assets/cards/king_of_clubs.png",
        Card(CLUBS, ACE) -> "assets/cards/ace_of_clubs.png",
        Card(SPADES, TWO) -> "assets/cards/2_of_spades.png",
        Card(SPADES, THREE) -> "assets/cards/3_of_spades.png",
        Card(SPADES, FOUR) -> "assets/cards/4_of_spades.png",
        Card(SPADES, FIVE) -> "assets/cards/5_of_spades.png",
        Card(SPADES, SIX) -> "assets/cards/6_of_spades.png",
        Card(SPADES, SEVEN) -> "assets/cards/7_of_spades.png",
        Card(SPADES, EIGHT) -> "assets/cards/8_of_spades.png",
        Card(SPADES, NINE) -> "assets/cards/9_of_spades.png",
        Card(SPADES, TEN) -> "assets/cards/10_of_spades.png",
        Card(SPADES, JACK) -> "assets/cards/jack_of_spades.png",
        Card(SPADES, QUEEN) -> "assets/cards/queen_of_spades.png",
        Card(SPADES, KING) -> "assets/cards/king_of_spades.png",
        Card(SPADES, ACE) -> "assets/cards/ace_of_spades.png",
        Card(HEARTS, TWO) -> "assets/cards/2_of_diamonds.png",
        Card(HEARTS, THREE) -> "assets/cards/3_of_diamonds.png",
        Card(HEARTS, FOUR) -> "assets/cards/4_of_diamonds.png",
        Card(HEARTS, FIVE) -> "assets/cards/5_of_diamonds.png",
        Card(HEARTS, SIX) -> "assets/cards/6_of_diamonds.png",
        Card(HEARTS, SEVEN) -> "assets/cards/7_of_diamonds.png",
        Card(HEARTS, EIGHT) -> "assets/cards/8_of_diamonds.png",
        Card(HEARTS, NINE) -> "assets/cards/9_of_diamonds.png",
        Card(HEARTS, TEN) -> "assets/cards/10_of_diamonds.png",
        Card(HEARTS, JACK) -> "assets/cards/jack_of_diamonds.png",
        Card(HEARTS, QUEEN) -> "assets/cards/queen_of_diamonds.png",
        Card(HEARTS, KING) -> "assets/cards/king_of_diamonds.png",
        Card(HEARTS, ACE) -> "assets/cards/ace_of_diamonds.png",
    )

    val cardImages: Map[Card, BufferedImage] = loadCardImages
    val cardBackside: BufferedImage = getCardBackside

    def loadCardImages: Map[Card, BufferedImage] = {
        cardToImagePath.map {
            case (card, path) =>
            print(path)
            card -> scaleImage(ImageIO.read(new File(path)), 0.20)
        }
    }

    def cardToImage(card: Card): BufferedImage = {
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
