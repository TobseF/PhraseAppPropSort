package tools.paps.config

import io.klogging.Level
import io.klogging.config.KloggingConfiguration
import io.klogging.config.SinkConfiguration
import io.klogging.config.loggingConfiguration
import io.klogging.events.LogEvent
import io.klogging.rendering.*
import io.klogging.sending.STDOUT


object LogConfig {
    private val COLOERED_CONSOLE_RENDER: RenderString = { e: LogEvent ->
        val message =
            "${e.timestamp.localTime} ${e.level.colour5} " +
                    " ${e.logger.right20} : ${e.evalTemplate()}"
        val maybeItems = if (e.items.isNotEmpty()) " : ${e.items}" else ""
        val maybeStackTrace = if (e.stackTrace != null) "\n${e.stackTrace}" else ""
        message + maybeItems + maybeStackTrace
    }

    /** Simple sink configuration for rendering ANSI-coloured strings to the standard output stream. */
    private val STDOUT_COLORED_CONFIG: SinkConfiguration =
        SinkConfiguration(COLOERED_CONSOLE_RENDER, STDOUT)

    fun coloredConsole(minLevel: Level = Level.INFO): KloggingConfiguration.() -> Unit = {
        sink("console", STDOUT_COLORED_CONFIG)
        logging { fromMinLevel(minLevel) { toSink("console") } }
    }

    fun setLogConfig(minLevel: Level = Level.INFO) {
        loggingConfiguration(block = coloredConsole())
    }


}