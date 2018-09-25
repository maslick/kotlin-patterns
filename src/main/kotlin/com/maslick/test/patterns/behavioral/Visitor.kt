package com.maslick.test.patterns.behavioral

/**
 * Created by maslick on 22/06/17.
 */


interface ReportVisitable {
    fun accept(visitor: ReportVisitor)
}

class FixedPriceContract(val costPerYear: Long) : ReportVisitable {
    override fun accept(visitor: ReportVisitor) = visitor.visit(this)
}

class TimeAndMaterialsContract(val costPerHour: Long, val hours: Long) : ReportVisitable {
    override fun accept(visitor: ReportVisitor) = visitor.visit(this)
}

class SupportContract(val costPerMonth: Long) : ReportVisitable {
    override fun accept(visitor: ReportVisitor) = visitor.visit(this)
}

interface ReportVisitor {
    fun visit(contract: FixedPriceContract)
    fun visit(contract: TimeAndMaterialsContract)
    fun visit(contract: SupportContract)
}

class MonthlyCostReportVisitor(var monthlyCost: Long = 0) : ReportVisitor {
    override fun visit(contract: FixedPriceContract) {
        monthlyCost += contract.costPerYear / 12
    }

    override fun visit(contract: TimeAndMaterialsContract) {
        monthlyCost += contract.costPerHour * contract.hours
    }

    override fun visit(contract: SupportContract) {
        monthlyCost += contract.costPerMonth
    }
}

fun main(args: Array<String>) {
    val projectAlpha = FixedPriceContract(costPerYear = 12000)
    val projectBeta = SupportContract(costPerMonth = 500)
    val projectGamma = TimeAndMaterialsContract(hours = 150, costPerHour = 1)
    val projectKappa = TimeAndMaterialsContract(hours = 50, costPerHour = 1)

    val projects = arrayOf(projectAlpha, projectBeta, projectGamma, projectKappa)

    val monthlyCostReportVisitor = MonthlyCostReportVisitor()
    projects.forEach { it.accept(monthlyCostReportVisitor) }
    println("Monthly cost: ${monthlyCostReportVisitor.monthlyCost}")
    
    
}