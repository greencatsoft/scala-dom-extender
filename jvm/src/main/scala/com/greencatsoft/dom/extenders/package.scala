package com.greencatsoft.dom

import scala.collection.JavaConverters._

import org.jsoup.nodes.Element

package object extenders {

	/**
	  * DOM Element를 스칼라에 알맞은 형태로 활용하고 확장하기 위한 헬퍼 클래스.
	  */
	implicit class ExtendedElement[T <: Element](val base: T) extends AnyVal {

		def childElements: Seq[Element] = base.children.asScala

		def query(selectors: String): Option[Element] = queryAll(selectors).headOption

		def queryAll(selectors: String): Seq[Element] = base.select(selectors).asScala
	}
}
