package com.github.ximera239.api.core

/**
 * User: Evgeny Zhoga
 * Date: 23.12.14
 */
trait Errors

case object TimeoutException extends Exception with Errors

case class ExceptionDuringCreation(reason: String) extends Exception with Errors