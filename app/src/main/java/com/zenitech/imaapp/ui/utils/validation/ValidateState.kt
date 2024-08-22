package com.zenitech.imaapp.ui.utils.validation

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class ValidateState<State: Any>(
    private val kClass: KClass<State>
) {
    fun validate(state: State): List<ValidationError?> {
        val errors = mutableListOf<ValidationError>()
        kClass.memberProperties.forEach {
            if (it.annotations.isEmpty())
                return@forEach

            val annotations = it.annotations
            val property = it.name
            val value = it.get(state)

            for(annotation in annotations){
                if (annotation is EmptyFieldValidation) {
                    if (isEmpty(value)) {
                        errors.add(ValidationError(property, "Must fill this field"))
                    }
                }
            }

        }

        return errors
    }

    private fun isEmpty(value: Any?): Boolean {
        return value.toString().isEmpty()
    }

}