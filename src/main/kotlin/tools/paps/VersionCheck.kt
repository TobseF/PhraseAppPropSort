package tools.paps

import tools.paps.VersionCheck.VersionCheckResult.State
import java.io.IOException
import java.net.URL


object VersionCheck {

    private const val versionFileURl =
        "https://raw.githubusercontent.com/TobseF/PhraseAppPropSort/master/version.info"

    /**
     * 00.00.00
     */
    private const val versionFormat = "\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}"

    fun checkForUpdates(): VersionCheckResult {
        val result = VersionCheckResult(Version.version)
        try {
            val url = URL(versionFileURl)
            val version = url.readText()
            if (version.matches(versionFormat.toRegex())) {
                if (Version.version == version) {
                    result.state = State.UpDoTate
                } else {
                    result.state = State.NewVersion
                    result.newVersion = version
                }
            }
        } catch (e: IOException) {
            println("Couldn't check for updates")
        }

        return result
    }

    class VersionCheckResult(val currentVersion: String) {
        var newVersion: String? = null
        var state = State.Unreachable

        enum class State {
            UpDoTate,
            NewVersion,
            Unreachable
        }
    }
}
