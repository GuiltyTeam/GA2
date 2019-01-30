package studio.Gulity.GA2.Engine.Data

import java.io.File

import akka.actor.Actor
import javax.sound.sampled.{AudioSystem, Clip}
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel.EventLevel

/**
  * ReCreated by Tamamo254 on 2019/1/12.
  */
class Alerter extends Actor {
  var InLaugh: Option[Clip] = None
  val Normal: Clip = AudioSystem.getClip()
  Normal.open(AudioSystem.getAudioInputStream(new File("./res/Alert/Normal.wav")))
  val Warning: Clip = AudioSystem.getClip()
  Warning.open(AudioSystem.getAudioInputStream(new File("./res/Alert/Warning.wav")))
  val Error: Clip
  = AudioSystem.getClip()
  Error.open(AudioSystem.getAudioInputStream(new File("./res/Alert/Error.wav")))
  val Fatal: Clip
  = AudioSystem.getClip()
  Fatal.open(AudioSystem.getAudioInputStream(new File("./res/Alert/Fatal.wav")))

  override def receive = {
    case alt: EventLevel =>
      alt match {
        case EventLevel.Fatal =>
          Fatal.setFramePosition(0)
          Fatal.loop(Clip.LOOP_CONTINUOUSLY)
          InLaugh = Some(Fatal)
        case EventLevel.Error =>
          Error.setFramePosition(0)
          Error.start()
        case EventLevel.Warning =>
          Warning.setFramePosition(0)
          Warning.start()
        case EventLevel.Normal =>
          Normal.setFramePosition(0)
          Normal.start()
        case EventLevel.Debug =>
      }
    case "Reset!" =>
      if (InLaugh.nonEmpty) {
        InLaugh.get.stop()
        InLaugh = None
      }
    case _ =>
  }
}
