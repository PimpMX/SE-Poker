package de.htwg.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.model.gameFieldComponent.gameFieldGenerator.GameGenerator
import de.htwg.model.fileIoComponent.fileIoJsonImpl.JSONFileIO
import de.htwg.model.fileIoComponent.fileIoXmlImpl.XMLFileIO
import de.htwg.controller.controllerBaseImpl.Controller
import java.io.File
import java.io.PrintWriter
import play.api.libs.json.Json
import de.htwg.model.gameFieldComponent.CardInterface
import play.api.libs.json.Reads
import play.api.libs.json.OFormat.oFormatFromReadsAndOWrites
import play.api.libs.json.Format.GenericFormat
import de.htwg.model.gameFieldComponent.cardBaseImpl._
import de.htwg.model.gameFieldComponent.SPADES
import de.htwg.model.gameFieldComponent.ACE
import play.api.libs.json._
import play.api.libs.functional.syntax._


class GameFieldPersistenceSpec extends AnyWordSpec with Matchers {

    "JSONFileIO" should {

        val gameGenerator = new GameGenerator
        val controller = new Controller
        

        "save and load the game field to/from a JSON file" in {
            val fileIO = new JSONFileIO
            val gameField = gameGenerator(1)     
            gameField.isDefined shouldBe true

            fileIO.save(gameField.get)

            controller.check()

            val loadedGameField = fileIO.load

            gameField.isDefined shouldBe true
            loadedGameField should be (gameField)
            gameField should not be (null)
        }

        "return a CardInterface with the correct values" in {
        // Erstellen Sie ein JsValue, das eine gültige Karte repräsentiert
        val json = Json.parse("""{"suit":"SPADES","rank":"ACE"}""")

        // Rufen Sie die cardReads-Methode auf, um ein CardInterface aus dem JsValue zu erstellen
        val
        val card = json.as[CardInterface]

        // Überprüfen Sie, ob das zurückgegebene CardInterface die erwarteten Werte hat
        card.getColor should be(SPADES)
        card.getRank should be(ACE)
      }
    }

    "XMLFileIO" should {

        val gameGenerator = new GameGenerator
        val fileIO = new XMLFileIO

        "save and load the game field to/from a XML file" in {
            val gameField = gameGenerator(1)
            gameField.isDefined shouldBe true

            fileIO.save(gameField.get)

            val loadedGameField = fileIO.load

            loadedGameField should be (gameField)
        }
    }
}




