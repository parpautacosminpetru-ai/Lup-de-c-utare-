import ro.lupaocr.app.KeywordMatcher
import ro.lupaocr.app.MatchConfig
import ro.lupaocr.app.MatchMode

private fun checkCase(name: String, value: Boolean) {
    if (!value) error("FAILED: $name")
    println("OK: $name")
}

fun main() {
    checkCase(
        "prefix matches inflection",
        KeywordMatcher.matches("universității", "univers", MatchConfig(MatchMode.STARTS_WITH))
    )
    checkCase(
        "diacritics ignored",
        KeywordMatcher.matches("universității", "universitatii", MatchConfig(MatchMode.EXACT, ignoreDiacritics = true))
    )
    checkCase(
        "exact rejects longer form",
        !KeywordMatcher.matches("universitate", "univers", MatchConfig(MatchMode.EXACT))
    )
    checkCase(
        "contains works",
        KeywordMatcher.matches("interuniversitar", "univers", MatchConfig(MatchMode.CONTAINS))
    )
    checkCase(
        "character limit truncates query in prefix mode",
        KeywordMatcher.matches("cercetare", "cercetător", MatchConfig(MatchMode.STARTS_WITH, characterLimit = 6))
    )
    checkCase(
        "literal phrase",
        KeywordMatcher.phraseOccurs(
            "Aceasta este o universitate publică importantă",
            "universitate publică",
            MatchConfig(MatchMode.EXACT)
        )
    )
    checkCase(
        "list keeps unrelated terms",
        KeywordMatcher.parseTerms("univers\nistorie\nmetodologie\nunivers").size == 3
    )
}
