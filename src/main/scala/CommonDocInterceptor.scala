import org.bson.Document
class CommonDocInterceptor extends MongoDocIntercepter {
  override def intercept(doc: Document): Document = {
    doc
  }
}
