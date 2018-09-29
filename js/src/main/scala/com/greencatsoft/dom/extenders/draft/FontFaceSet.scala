package com.greencatsoft.dom.extenders.draft

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

/**
 * FontFaceSet의 Scala.js 퍼서드
 * https://developer.mozilla.org/en-US/docs/Web/API/FontFaceSet
 *
 * MDN
 */
@JSGlobal
@js.native
class FontFaceSet extends js.Object {

  /**
   * https://developer.mozilla.org/en-US/docs/Web/API/FontFaceSet/check
   *
   * MDN
   */
  def check(font: String): Boolean = js.native

  def check(font: String, text: String): Boolean = js.native

  /**
   * https://developer.mozilla.org/en-US/docs/Web/API/FontFaceSet/load
   *
   * MDN
   */
  def load(font: String): js.Promise[js.Array[FontFace]] = js.native

  def load(font: String, text: String): js.Promise[js.Array[FontFace]] = js.native

  /**
   * https://developer.mozilla.org/en-US/docs/Web/API/FontFaceSet/ready
   *
   * MDN
   */
  var ready: js.Promise[FontFaceSet] = js.native
}
