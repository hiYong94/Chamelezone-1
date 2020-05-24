package tk.yeonaeyong.shopinshop.util

import android.util.Log

object Logger {
    private var debugFlag = true
    fun v(msg: String) {
        myLogger(Log.VERBOSE, msg)
    }

    fun d(msg: String) {
        myLogger(Log.DEBUG, msg)
    }

    fun i(msg: String) {
        myLogger(Log.INFO, msg)
    }

    fun w(msg: String) {
        myLogger(Log.WARN, msg)
    }

    fun e(msg: String) {
        myLogger(Log.ERROR, msg)
    }

    private fun myLogger(prior: Int, msg: String) {
        if (debugFlag) {
            val logBuilder = StringBuilder()
            logBuilder.append("[").append(Thread.currentThread().stackTrace[4].methodName)
                .append("()").append("]").append(" :: ").append(msg)
                .append(" (").append(Thread.currentThread().stackTrace[4].fileName).append(":")
                .append(Thread.currentThread().stackTrace[4].lineNumber).append(")")
            Log.println(
                prior,
                Thread.currentThread().stackTrace[4].fileName.replace(".kt", "").replace(
                    ".java",
                    ""
                ),
                "$logBuilder"
            )
        }
    }
}