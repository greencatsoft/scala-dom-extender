package com.greencatsoft.dom.extenders.draft

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.scalajs.js.annotation.JSGlobal

/**
 * Error의 Scala.js 퍼서드
 */
@JSGlobal
@js.native
class Error extends js.Object {

  // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Error/stack
  val stack: UndefOr[js.Any] = js.native
}
