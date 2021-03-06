package cc.theurgist.motrack.ui.actors.command

import akka.actor.{ActorRef, ActorSystem, _}
import akka.stream.Materializer
import cats.effect.IO
import cc.theurgist.motrack.lib.messages.{Exit, GetAccounts, LoginAttempt, Logoff, UpdateServerStatus}
import cc.theurgist.motrack.lib.security.{LoginData, SecBundle}
import com.typesafe.scalalogging.StrictLogging

/**
  * Main interaction interface
  *
  * @param commandActor command actor
  * @param owner actor which will receive command execution results
  */
class CommandInterface private(commandActor: ActorRef, sb: Option[SecBundle] = None)(implicit owner: ActorRef) extends StrictLogging {
  logger.debug(s"CommandInterface for actor: $commandActor with owner: $owner")

  /**
    * Let another actor have it's own copy of this interface
    */
  def lend(forOwner: ActorRef): CommandInterface = new CommandInterface(commandActor, sb)(forOwner)
  def changeSec(newSb: Option[SecBundle]): CommandInterface = new CommandInterface(commandActor, newSb)(owner)

  def login(username: String, password: String): Unit = commandActor ! LoginAttempt(LoginData(username, password))
  def logoff(): Unit = commandActor ! (sb, Logoff)

  def updateServerStatus(): Unit = commandActor ! UpdateServerStatus()

  def queryAccounts(): Unit = commandActor ! (sb, GetAccounts)

  def exit(): Unit = commandActor ! Exit
}

/**
  * Command interface initialization entry point
  */
object CommandInterface {

  /**
    * Get a command interface
    *
    * @param name new actor's name
    * @param owner action results receiving actor
    * @param system actor system
    * @param m actor materializer
    * @return wrapped command interface instance
    */
  def init(name: String, owner: ActorRef = ActorRef.noSender)(implicit system: ActorSystem,
                                                              m: Materializer): IO[CommandInterface] =
    for {
      ref <- IO(system.actorOf(CommandActor.props, name))
      res <- IO(new CommandInterface(ref)(owner))
      //_   <- IO(ref ! 'listActors)
    } yield res
}
