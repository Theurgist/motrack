package cc.theurgist.motrack.server.database.dal

import cc.theurgist.motrack.lib.model.security.session.{Session, SessionId}
import cc.theurgist.motrack.lib.model.security.user.UserId
import cc.theurgist.motrack.server.database.Db.InmemContext
import com.typesafe.scalalogging.StrictLogging

class SessionDAO(implicit context: InmemContext) extends BaseCRUD[Session](context) with StrictLogging {

  import ctx._
  private val sessions = quote(querySchema[Session]("sessions"))

  def create(o: Session): SessionId = ctx.transaction {
    delete(o.userId)
    ctx.run(quote(sessions.insert(lift(o)).returning(_.id)))
  }

  def delete(id: SessionId): Long = ctx.run { byId(id).delete }
  def delete(id: UserId): Long    = ctx.run { byUserId(id).delete }

  def find(id: SessionId): Option[Session] = { extractUnique(ctx.run(quote { byId(id) }), s"id '$id'") }
  def find(id: UserId): Option[Session]    = { extractUnique(ctx.run(quote { byUserId(id) }), s"userId '$id'") }

  private def byId(id: SessionId)  = quote { sessions.filter(_.id == lift(id)) }
  private def byUserId(id: UserId) = quote { sessions.filter(_.userId == lift(id)) }

}
