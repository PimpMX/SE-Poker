package de.htwg

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

class TexasHoldEmModule extends AbstractModule with ScalaModule {
  
    override def configure() = {
        bind[ControllerInterface].to[Controller]
        bind[GameFieldFactoryInterface].to[GameFieldFactory]
        bind[PlayerBuilderInterface].to[PlayerBuilder]
        bind[CommunityCardFactoryInterface].to[CommunityCardFactory]
        bind[CommunityCardsFactoryInterface].to[CommunityCardsFactory]
        bind[GameGeneratorInterface].to[GameGenerator]
    }
}
