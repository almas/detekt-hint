package io.gitlab.arturbosch.detekt.sample.extensions.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction

/**
 * This is a sample rule reporting too many functions inside a file.
 */
class TooManyFunctions(config: Config) : Rule(config) {

    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "This rule reports a file with an excessive function count.",
        Debt.TWENTY_MINS
    )

    private var amount: Int = 0

    override fun visitKtFile(file: KtFile) {
        super.visitKtFile(file)
        if (amount > THRESHOLD) {
            report(CodeSmell(issue, Entity.from(file),
                message = "The file ${file.name} has $amount function declarations. " +
                    "Threshold is specified with $THRESHOLD."))
        }
        amount = 0
    }

    override fun visitNamedFunction(function: KtNamedFunction) {
        amount++
    }
}

const val THRESHOLD = 1
