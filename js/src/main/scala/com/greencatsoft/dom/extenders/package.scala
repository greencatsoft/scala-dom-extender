package com.greencatsoft.dom

import scala.language.implicitConversions

import scala.scalajs.js

import org.scalajs.dom._
import org.scalajs.dom.ext.EasySeq
import org.scalajs.dom.svg.AnimatedLengthList

import com.greencatsoft.d3.common.Bounds
import com.greencatsoft.d3.common.Bounds.clientRect2Bounds
import com.greencatsoft.dom.extenders.draft._

package object extenders {

  // TODO org.scalajs.dom.ext 패키지에 업스트림 반영
  implicit class PimpedTokenList(tokens: DOMTokenList)
    extends EasySeq[String](tokens.length, tokens.apply)

  /**
   * DOM Node를 스칼라에 알맞은 형태로 활용하고 확장하기 위한 헬퍼 클래스.
   */
  implicit class ExtendedNode[T <: Node](val base: T) extends AnyVal {

    /**
     * element.appendChild의 결과는 추가된 객체 자신이지만 scalajs-dom의 한계상 유형이 Node로 고정된다.
     * 이것을 다시 변경하는 불편을 없애기 위해 만들어진 메서드.
     * @param newChild 자식으로 추가될 요소
     * @tparam A 추가될 요소의 유형
     * @return 추가된 요소
     */
    def append[A <: Node](newChild: A): A = base.appendChild(newChild).asInstanceOf[A]

    def removeFromParent(): T = base.parentNode.removeChild(base).asInstanceOf[T]

    def replaceWith[A <: Node](target: A): A = base.parentNode.replaceChild(target, base).asInstanceOf[A]
  }

  /**
   * DOM NodeSelector를 스칼라에 알맞은 형태로 활용하고 확장하기 위한 헬퍼 클래스.
   */
  implicit class ExtendedNodeSelector[T <: NodeSelector](val base: T) extends AnyVal {

    import org.scalajs.dom.ext.PimpedNodeList

    def getAs[E <: Element](selectors: String): E = base.querySelector(selectors).asInstanceOf[E]

    def query(selectors: String): Option[Element] = Option(base.querySelector(selectors))

    def queryAs[E <: Element](selectors: String): Option[E] = query(selectors).map(_.asInstanceOf[E])

    /**
     * PimpedNodeList를 일일이 import할 필요 없도록 하는 도움 클래스. PimpedNodeList는 NodeList를
     * EasySeq로 만들어 반환하며, SeqLike.toSeq의 결과는 ThisCollection이기 때문에 성능 저하가 적다.
     * 당초 select/selectAll을 고려했으나 D3에 동일한 이름의 메서드가 있어 부득이 구분하였다.
     * @param selectors 쿼리로 선택할 선택자
     * @return 검색 결과 노드 시퀀스
     */
    def queryAll(selectors: String): Seq[Node] = base.querySelectorAll(selectors).toSeq

    def queryAllAs[E <: Element](selectors: String): Seq[E] = queryAll(selectors).asInstanceOf[Seq[E]]
  }

  /**
   * DOM Element를 스칼라에 알맞은 형태로 활용하고 확장하기 위한 헬퍼 클래스.
   */
  implicit class ExtendedElement[T <: Element](val base: T) extends AnyVal {

    import org.scalajs.dom.ext.{ PimpedHtmlCollection, PimpedNodeList }

    /**
     * Element.getBoundingClientRect의 결과를 Bounds 객체로 반환한다.
     * 회전된 상태의 ForeignObject에 포함된 객체는 비율이 완전히 달라진 잘못된 크기(회전된 객체를
     * 포함하는 회전되지 않은 더 큰 정사각형)를 반환한다. ForeignObject 바깥에서 구한 Bounds에
     * ForeignObject의 CTM을 적용해도 같은 결과가 나온다. (크롬 65 기준)
     * FireFox에서는 제대로 된 값이 구해진다.
     * @return Bounds로 변환된 BoundingClientRect
     */
    def boundingClientRect: Bounds = base.getBoundingClientRect

    def childElements: Seq[Element] = if (js.isUndefined(base.children)) {
      base.childNodes.collect { case e: Element => e }
    } else {
      base.children.toSeq
    }

    def childElementsAs[E <: Element]: Seq[E] = childElements.asInstanceOf[Seq[E]]

    def cloneElement(deep: Boolean = true): T = base.cloneNode(deep).asInstanceOf[T]

    def asText: String = {
      def collectText(node: Node): Traversable[String] = {
        node match {
          case elem: Element => elem.childNodes.flatMap(collectText)
          case text: Text => Seq(text.textContent)
          case _ => Nil
        }
      }

      collectText(base).mkString
    }

    def clearContent(filter: Node => Boolean = _ => true) {
      base.childNodes.toSeq.filter(filter).reverse.foreach(base.removeChild)
    }

    // TODO jQuery.browser.msie는 Boolean이 아닌 UndefOr[Boolean]이 되어야 한다. 업스트림 패치 필요
    import js.Dynamic.global

    def queryChild(selectors: String): Option[Element] = if (js.isUndefined(global.jQuery.browser.msie)) {
      base.query(":scope > " + selectors)
    } else {
      require(base.id.nonEmpty)
      base.query(s"# ${ base.id } > $selectors")
    }

    def queryChildAs[E <: Element](selectors: String): Option[E] = queryChild(selectors).map(_.asInstanceOf[E])

    def queryChildren(selectors: String): Seq[Element] = if (js.isUndefined(global.jQuery.browser.msie)) {
      base.queryAllAs(":scope > " + selectors)
    } else {
      require(base.id.nonEmpty)
      base.queryAllAs(s"# ${ base.id } > $selectors")
    }

    def queryChildrenAs[E <: Element](selectors: String): Seq[E] = queryChildren(selectors).asInstanceOf[Seq[E]]

    /**
     * Element.closest의 스칼라 버전.
     * @param selectors 쿼리로 선택할 선택자
     * @return 검색 결과 노드
     */
    def queryClosest(selectors: String): Option[Element] = Option(base.closest(selectors))

    def queryClosestAs[E <: Element](selectors: String): Option[E] = queryClosest(selectors).map(_.asInstanceOf[E])
  }

  /**
   * DOM AdnimatedLengthList를 스칼라에 알맞은 형태로 활용하고 확장하기 위한 헬퍼 클래스.
   */
  implicit class ExtendedAnimatedLengthList(val attr: AnimatedLengthList) extends AnyVal {

    def value: Option[Double] = if (attr.baseVal.numberOfItems > 0) {
      Some(attr.baseVal.getItem(0).value)
    } else {
      None
    }
  }
}
