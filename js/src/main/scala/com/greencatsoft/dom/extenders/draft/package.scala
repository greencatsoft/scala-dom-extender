package com.greencatsoft.dom.extenders

import scala.language.implicitConversions

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

import org.scalajs.dom._

package object draft {

	// Scalajs-DOM API는 정책상 실험적인(Experimental) API는 포함하지 않는다.
	// draft 패키지 및 아래의 암묵 변환은 그런 실험적이거나 제출 상태의 API를 강타입으로 활용하기 위한 도구들이다.
	implicit def toNonStandardError(error: js.Error): draft.Error = error.asInstanceOf[draft.Error]

	@js.native
	@JSGlobal
	abstract class NonStandardDocument extends Document {

		def fonts: FontFaceSet = js.native
	}

	implicit def toNonStandardDocument(doc: Document): NonStandardDocument =
		doc.asInstanceOf[NonStandardDocument]

	@js.native
	@JSGlobal
	class NonStandardElement extends Element {

		/**
		  * The Element.closest() method returns the closest ancestor of the current element (or the
		  * current element itself) which matches the selectors given in parameter. If there isn't such
		  * an ancestor, it returns null.
		  *
		  * MDN
		  */
		def closest(selectors: String): Element = js.native
	}

	implicit def toNonStandardElement(elem: Element): NonStandardElement =
		elem.asInstanceOf[NonStandardElement]
}
