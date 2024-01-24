package de.htwg.view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File
import de.htwg.model.gameFieldComponent.cardBaseImpl._
import de.htwg.model.gameFieldComponent._
import java.awt.Image
import de.htwg.view.CardImages
import scala.collection.immutable.Map


class CardImagesSpec extends AnyWordSpec with Matchers {    
    "CardImages" should {

        val cardImages = new CardImages

        cardImages.cardToImagePath.foreach {
            case (card, path) => 
            s"Load the image for card $card" in {
                val image = cardImages.cardToImage(card)
                image should be theSameInstanceAs cardImages.cardToImage(card)
                image.getWidth should be > 0
                image.getHeight should be > 0
            }
        }

        "load the backside image correctly" in {
            val backsideImage = cardImages.cardBackside

            backsideImage should not be null
            backsideImage.getWidth should be > 0
            backsideImage.getHeight should be > 0
        }

        "scale images correctly" in {
            val originalImage = ImageIO.read(new File("assets/cards/2_of_hearts.png"))
            val scale = 0.5

            val scaledImage = cardImages.scaleImage(originalImage, scale)

            scaledImage should not be null
            scaledImage.getWidth should be((originalImage.getWidth * scale).toInt)
            scaledImage.getHeight should be((originalImage.getHeight * scale).toInt)
        }
    }
}    



