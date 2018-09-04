import org.bson.Document
trait MongoDocIntercepter {
  def intercept(doc:Document):Document
}
