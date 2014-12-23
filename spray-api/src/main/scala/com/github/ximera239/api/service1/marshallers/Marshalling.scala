package com.github.ximera239.api.service1.marshallers

import org.json.JSONObject
import com.github.ximera239.model.BusinessObject
import spray.http._
import spray.httpx.marshalling.Marshaller
import spray.httpx.unmarshalling.Unmarshaller

/**
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
object Marshalling {
  val `application/atom+json` = MediaTypes.register(MediaType.custom("application/atom+json"))
  val `application/json` = MediaTypes.`application/json`

  object ContentTypes {
    val `application/atom+json` = Marshalling.`application/atom+json`.withCharset(HttpCharsets.`UTF-8`)
    val `application/json` = Marshalling.`application/json`.withCharset(HttpCharsets.`UTF-8`)
  }
  val supportedContentTypes = List(ContentTypes.`application/atom+json`, ContentTypes.`application/json`)


  private[marshallers] object json {
    private[marshallers] object atom {
      val marshaller =
        Marshaller.of[BusinessObject](`application/atom+json`) { (value, contentType, ctx) ⇒
          val BusinessObject(id, description) = value
          val string = new JSONObject().put("id", id).put("description", s"to atom+json $description").toString
          ctx.marshalTo(HttpEntity(contentType, string))
        }

      val unmarshaller =
        Unmarshaller[BusinessObject](`application/atom+json`) {
          case HttpEntity.NonEmpty(contentType, data) =>
            // unmarshal from the string format used in the marshaller example
            val json = new JSONObject(data.asString)
            BusinessObject(json.getString("id"), s"""from atom+json [${json.getString("description")}]""")

          // if we had meaningful semantics for the HttpEntity.Empty
          // we could add a case for the HttpEntity.Empty:
          // case HttpEntity.Empty =>
        }
    }
    val marshaller =
      Marshaller.of[BusinessObject](`application/json`) { (value, contentType, ctx) ⇒
        val BusinessObject(id, description) = value
        val string = new JSONObject().put("id", id).put("description", s"to json $description").toString
        ctx.marshalTo(HttpEntity(contentType, string))
      }

    val unmarshaller =
      Unmarshaller[BusinessObject](`application/json`) {
        case HttpEntity.NonEmpty(contentType, data) =>
          // unmarshal from the string format used in the marshaller example
          val json = new JSONObject(data.asString)
          BusinessObject(json.getString("id"), s"""from json [${json.getString("description")}]""")
      }
  } 

  implicit val unmarshaller = Unmarshaller.oneOf[BusinessObject](
    json.atom.unmarshaller,
    json.unmarshaller
  )

  implicit val marshaller =
    Marshaller[BusinessObject] { (foo, ctx) =>
      ctx.tryAccept(supportedContentTypes) match {
        case Some(ContentTypes.`application/json`) => json.marshaller(foo, ctx)
        case Some(ContentTypes.`application/atom+json`) => json.atom.marshaller(foo, ctx)
        case _ => ctx.rejectMarshalling(supportedContentTypes)
      }
    }
}

