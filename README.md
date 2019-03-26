scala-dom-extender
================================

## Introduction
Extend DOM elements for easy manipulation in scala and scala.js.

### How to Use

### SBT Settings

Add the following lines to your ```sbt``` build definition:

```scala
libraryDependencies += "com.greencatsoft" %%% "scala-dom-extender" % "0.1-SNAPSHOT"
```

And add Sonatype snapshot repository to the resolver list as follows:

```scala
resolvers += Resolver.sonatypeRepo("snapshots")
```

### Example
Scala.js :
```scala
import com.greencatsoft.dom.extenders._
import org.scalajs.dom._
import org.scalajs.dom.html.{Element => HTMLElement, Option => HTMLOption, _}
import org.scalajs.dom.{Node, document => doc}

// scalajs-dom
val elem: Element = doc.querySelector("#selectBox")
// scala-dom-extender
val select: Select = doc.getAs[Select](s"#selectBox")
val elemOpt: Option[Element] = doc.query(s"#selectBox")
val selectOpt: Option[Select] = doc.queryAs[Select](s"#selectBox")

// scalajs-dom
val nodes: NodeList = doc.querySelectorAll("select")
// scala-dom-extender
val nodeSeq: Seq[Node] = doc.queryAll(s"select")
val selects: Seq[Select] = doc.queryAllAs[Select](s"select")

val opt = doc.createElement("option").asInstanceOf[HTMLOption]
val opt2 = doc.createElement("option").asInstanceOf[HTMLOption]

// scalajs-dom
val node: Node = elem.appendChild(opt)
// scala-dom-extender
val option: HTMLOption = elem.append(opt)

// scalajs-dom
val children: NodeList = elem.childNodes
// scala-dom-extender
val elements: Seq[Element] = elem.childElements
val options: Seq[HTMLOption] = elem.childElementsAs[HTMLOption]

// scalajs-dom
val child: Element = elem.querySelector(":scope > option")
// scala-dom-extender
val childOpt: Option[Element] = elem.queryChild("option")
val option2: Option[HTMLOption] = elem.queryChildAs[HTMLOption]("option")

// scalajs-dom
val nodes2: NodeList = elem.querySelectorAll(":scope > option")
// scala-dom-extender
val elements3: Seq[Element] = elem.queryChildren("option")
val options2: Seq[HTMLOption] = elem.queryChildrenAs[HTMLOption]("option")

// scalajs-dom + scala-dom-extender
val closest: Element = option.asInstanceOf[draft.NonStandardElement].closest("select")
val closest2: Option[Element] = option.queryClosest("select")
val closest3: Option[Select] = option.queryClosestAs[Select]("select")

// scalajs-dom
val newElem: Node = select.cloneNode(true)
// scala-dom-extender
val newSelect: Select = select.cloneElement(true)

// scalajs-dom
val removed: Node = elem.replaceChild(opt, opt2)
// scala-dom-extender
val removedOpt: HTMLOption = opt.replaceWith(opt2)

// scalajs-dom
elem.removeChild(node)
// scala-dom-extender
option.removeFromParent()
```

### License

This project is provided under the terms of _Apache License, Version 2.0_. 
