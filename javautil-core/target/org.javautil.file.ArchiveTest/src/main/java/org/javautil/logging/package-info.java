/**
 * Classes for logging in applications, similar to the package java.logging.
 * 
 * This package is based on the concept of events which are both finer grained
 * than the various logger levels as they can be used to toggle the reporting 
 * of specific actions.  Events are also cross cutting in that an event may 
 * be in multiple methods, classes, even packages.
 * 
 * These methods are an extension of the Event mechanism from dbexperts and subsume that effort.
 * 
 * todo need example usage
 * @author jjs
 */
package org.javautil.logging;