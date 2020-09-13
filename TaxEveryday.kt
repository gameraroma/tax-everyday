/**
 * You can edit, run, and share this code. 
 * play.kotlinlang.org 
 */

import java.time.LocalDate
import java.time.YearMonth
import kotlin.math.min

fun main() {
    val year = 2020
    val yeardt = LocalDate.of(year, 1, 1)
    val payment = 10000.00
    var principal: Double = 1500000.00
    val percentOfInterest = 0.03
    var sumOfInterest: Double = 0.00
    
    var newPrincipal = principal
    var newSumOfInterest: Double = 0.00
    
    val extraPayment = 180000
    var atMonth = 11
    
    for (monthInt in 1..12) {
        val yearMonthObject = YearMonth.of(year, monthInt);
		val daysInMonth = yearMonthObject.lengthOfMonth();
        
        if (monthInt == atMonth) {
            newPrincipal -= extraPayment
        }
        
        val interest = (principal * percentOfInterest * daysInMonth) / yeardt.lengthOfYear()
        sumOfInterest += interest
        principal = principal - (payment - interest)
       
        val newInterest = (newPrincipal * percentOfInterest * daysInMonth) / yeardt.lengthOfYear()
        newSumOfInterest += newInterest
        newPrincipal = newPrincipal - (payment - newInterest)
    }
    
    println("เงินต้นคงเหลือ = $principal")
    println("เงินต้นคงเหลือหลังโปะ = $newPrincipal")
    println("ดอกเบี้ยรวม = $sumOfInterest")
    println("ดอกเบี้ยรวมหลังโปะ = $newSumOfInterest")
    
    println("ประหยัดดอกเบี้ยไป = " + (sumOfInterest - newSumOfInterest))
    
    println("-------------------------------------------------------------")
    // สำหรับผู้มีรายได้เกิน 15,000 บาท
    val monthlyIncome: Double = 40000.00
    var yearlyIncome = 12 * monthlyIncome
    
    val investedInSSF: Double = 0.00
    val investedInRMF: Double = 0.00
    
    val maxBreakForSSF = min(0.3 * yearlyIncome, investedInSSF)
    val maxBreakForRMF = min(0.3 * yearlyIncome, investedInRMF)
    
    val hasHomeLoan = false
    val numberOfOwner = 1
    val numberOfParent = 0
    val numberOfChildren = 0
    val marriedWithNoIncome = false
    
    // 100000 = รายจ่าย, 60000 = ลดหย่อนเมื่อมีรายได้, 9000 = ประกันสังคม
    sumOfInterest = if (hasHomeLoan) sumOfInterest else 0.00
    val breakForIncome = min(100000.00, yearlyIncome / 2)
    val breakForIncomeMarried = if (marriedWithNoIncome) 60000.00 else 0.00
    val yearlyIncomeWithOutFunding = yearlyIncome - breakForIncome - 60000.00 - 9000.00
    							- (sumOfInterest / numberOfOwner) 
    							- (numberOfParent * 30000.00)
    							- (numberOfChildren * 30000.00)
    yearlyIncome = yearlyIncomeWithOutFunding - maxBreakForSSF - maxBreakForRMF
    
    var calculatedIncome: Double = 0.00
    var currentRate = 1
    var totalTaxOwed: Double = 0.00
    while (calculatedIncome < yearlyIncome) {
        var taxableIncome: Double = 0.00
        var taxRate: Double = 0.00
        when (currentRate) {
            1 -> {
                taxRate = 0.00
                taxableIncome = min(150000.00, yearlyIncome - calculatedIncome)
            	totalTaxOwed += taxableIncome * taxRate
            }
            2 -> {
                taxRate = 0.05
                taxableIncome = min(150000.00, yearlyIncome - calculatedIncome)
            	totalTaxOwed += taxableIncome * taxRate
            }
            3 -> {
                taxRate = 0.10
                taxableIncome = min(200000.00, yearlyIncome - calculatedIncome)
            	totalTaxOwed += taxableIncome * taxRate
            }
            4 -> {
                taxRate = 0.15
                taxableIncome = min(250000.00, yearlyIncome - calculatedIncome)
            	totalTaxOwed += taxableIncome * taxRate
            }
            5 -> {
                taxRate = 0.20
                taxableIncome = min(250000.00, yearlyIncome - calculatedIncome)
            	totalTaxOwed += taxableIncome * taxRate
            }
            6 -> {
                taxRate = 0.25
                taxableIncome = min(1000000.00, yearlyIncome - calculatedIncome)
            	totalTaxOwed += taxableIncome * taxRate
            }
            7 -> {
                taxRate = 0.30
                taxableIncome = min(3000000.00, yearlyIncome - calculatedIncome)
            	totalTaxOwed += taxableIncome * taxRate
            }
        }
        currentRate += 1
        calculatedIncome += taxableIncome
    }
    
    if (yearlyIncomeWithOutFunding <= 150000.00) {
    	println("ยินดีด้วย คุณไม่ต้องเสียภาษี ^_^")
        return
    }
    println("ภาษีที่ต้องจ่าย(ไม่มีการโปะบ้าน) = $totalTaxOwed")
    println("")
   
    val shouldInvestAmount = yearlyIncomeWithOutFunding - 150000
    println("")
    println("จำนวนเงินลงทุนในกองทุนลดหย่อนภาษีขั้นต่ำ ในกรณีที่จะไม่เสียภาษีเลย")
    printRecommendText(shouldInvestAmount, payment, monthlyIncome)
    
    currentRate -= 1
    var highestRate = 0.00
    if (currentRate == 2) {
        return
    }
    else if (currentRate == 3) {
        highestRate = 300000.00
    }
    else if (currentRate == 4) {
        highestRate = 500000.00
    }
    else if (currentRate == 5) {
        highestRate = 750000.00
    }
    else if (currentRate == 6) {
        highestRate = 1000000.00
    }
    else if (currentRate == 7) {
        highestRate = 2000000.00
    }
    
    val shouldInvestMinAmount = yearlyIncomeWithOutFunding - highestRate
    println("")
    println("จำนวนเงินลงทุนในกองทุนลดหย่อนภาษีอย่างน้อยที่สุด เพื่อลดภาษีได้มากขึ้น")
    printRecommendText(shouldInvestMinAmount, payment, monthlyIncome)
}

fun printRecommendText(shouldInvestAmount: Double, payment: Double, monthlyIncome: Double) {
    if (shouldInvestAmount <= 0.3 * 12 * monthlyIncome) {
        println("SSF หรือ RMF อย่างเดียว: $shouldInvestAmount")
    } else {
    	println("ลงไปยัง SSF หรือ RMF: ${0.3 * 12 * monthlyIncome}")
        println("อีกชนิดกองทุน: ${shouldInvestAmount - (0.3 * 12 * monthlyIncome)}")
    }
    println("หรือทั้ง SSF และ RMF อย่างละครึ่ง: ${shouldInvestAmount / 2}")
    println("- ตกเดือนละ ${shouldInvestAmount / 12}")
    println("- คิดเป็น ${((shouldInvestAmount / 12) / (12 * monthlyIncome) * 100)} % ของเงินเดือน")
    println("- เหลือเงินไว้ใช้ ${monthlyIncome - payment - (shouldInvestAmount / 12)}")
}
