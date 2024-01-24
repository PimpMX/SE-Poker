package de.htwg.view

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

import de.htwg.controller.ControllerInterface
import de.htwg.controller.controllerBaseImpl.Controller

import de.htwg.model.gameFieldComponent._
import de.htwg.model.gameFieldComponent.gameFieldBaseImpl._
import de.htwg.model.gameFieldComponent.playerBaseImpl._
import de.htwg.model.gameFieldComponent.comCardBaseImpl._
import de.htwg.model.gameFieldComponent.comCardsBaseImpl._
import de.htwg.model.gameFieldComponent.gameFieldGenerator.GameGenerator
import de.htwg.model.gameFieldComponent.cardBaseImpl.CardFactory
import de.htwg.model.gameFieldComponent.cardSetBaseImpl.CardSet
import de.htwg.model.gameFieldComponent.cardSetBaseImpl.CardSetFactory
import de.htwg.model.fileIoComponent.fileIoJsonImpl.JSONFileIO
import de.htwg.model.fileIoComponent.fileIoXmlImpl.XMLFileIO
import de.htwg.model.fileIoComponent.FileIOInterface
import de.htwg.model.gameFieldComponent.playerBaseImpl.PlayerFactory

class TestModule extends AbstractModule with ScalaModule {
    override def configure() = {
        bind[CardFactoryInterface].to[CardFactory]
        bind[ControllerInterface].to[Controller]
        bind[GameFieldFactoryInterface].to[GameFieldFactory]
        bind[PlayerFactoryInterface].to[PlayerFactory]
        bind[CommunityCardFactoryInterface].to[CommunityCardFactory]
        bind[CommunityCardsFactoryInterface].to[CommunityCardsFactory]
        bind[GameGeneratorInterface].to[GameGenerator]
        bind[CardSetFactoryInterface].to[CardSetFactory]
        bind[FileIOInterface].to[XMLFileIO]
    }
}
