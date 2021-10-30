package com.sharefinancialledger.global.entity.type

import javax.persistence.AttributeConverter
import javax.persistence.Converter


enum class TransactionType(val dbValue: Int) {
    INCOME(1), EXPENDITURE(2);
}


@Converter(autoApply = true)
class TransactionTypeConverter : AttributeConverter<TransactionType, Int> {
    override fun convertToDatabaseColumn(attribute: TransactionType): Int {
        return attribute.dbValue
    }

    override fun convertToEntityAttribute(dbData: Int): TransactionType {
        return TransactionType.values().first { it.dbValue == dbData }
    }

}

