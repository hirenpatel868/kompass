package io.sellmair.kompass.internal.pipe

import android.support.annotation.AnyThread
import android.support.annotation.UiThread
import io.sellmair.kompass.internal.precondition.Precondition
import io.sellmair.kompass.internal.precondition.requireMainThread
import io.sellmair.kompass.internal.util.mainThread


/*
################################################################################################
INTERNAL API
################################################################################################
*/

internal operator fun <In, Intermediate, Out> InstructionPipe<In, Intermediate>.plus(
    other: InstructionPipe<Intermediate, Out>): InstructionPipe<In, Out> {
    return InstructionPipePlusConnector(this, other)
}


/*
################################################################################################
PRIVATE IMPLEMENTATION
################################################################################################
*/

private class InstructionPipePlusConnector<In, Intermediate, Out>(
    private val first: InstructionPipe<In, Intermediate>,
    second: InstructionPipe<Intermediate, Out>) :
    InstructionPipe<In, Out> {

    private var handler: ((Out) -> Unit)? = null

    @UiThread
    override fun invoke(payload: In) {
        Precondition.requireMainThread()
        first(payload)
    }

    @UiThread
    override fun handle(handler: (out: Out) -> Unit) {
        Precondition.requireMainThread()
        this.handler = handler
    }

    @AnyThread
    override fun handle(value: Out) = mainThread {
        handler?.invoke(value)
    }


    init {
        first.handle(second::invoke)
        second.handle(this::handle)
    }
}