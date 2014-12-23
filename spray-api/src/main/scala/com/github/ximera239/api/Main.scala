package com.github.ximera239.api

import org.slf4j.LoggerFactory
import com.github.ximera239.api.core.{ActorsCore, BootedCore}

import scala.concurrent.ExecutionContext
import scala.util.matching.Regex

/**
 * Base example:  https://github.com/eigengo/activator-akka-spray/blob/master/tutorial/index.md
 *
 * Unmarshalling: https://groups.google.com/forum/#!topic/spray-user/UQ6LE455b7k
 * Marshalling: https://groups.google.com/forum/#!topic/spray-user/xtawBl2Ytx4
 *
 * directives composition http://alterstack.io/blog/2013/10/02/binding-directives-in-spray
 *
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
object Main extends App with BootedCore with ActorsCore with Api with Web  {
  LoggerFactory.getLogger(getClass).info("started")
}






















