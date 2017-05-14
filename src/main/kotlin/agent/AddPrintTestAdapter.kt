package agent

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ASM4
import org.objectweb.asm.Opcodes.ACC_INTERFACE

class AddPrintTestAdapter(cv: ClassVisitor?) : ClassVisitor(ASM4, cv) {
    private var owner : String? = null
    private var isInterface = false
    private val METHOD_TO_CATCH_NAME = "test"
    private val METHOD_TO_CATCH_PARAMS = "(Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;"
    private val METHOD_TO_CATCH_OWNER = "example/CoroutineExampleKt"

    override fun visitMethod(access: Int, name: String?, desc: String?, signature: String?,
                             exceptions: Array<out String>?): MethodVisitor {
        var mv = cv.visitMethod(access, name, desc, signature, exceptions)
        if (!isInterface && mv != null && name.equals(METHOD_TO_CATCH_NAME) &&
                desc.equals(METHOD_TO_CATCH_PARAMS) && owner.equals(METHOD_TO_CATCH_OWNER)) {
            mv = AddPrintTestMethodAdapter(mv)
        }
        return mv
    }

    override fun visit(version: Int, access: Int, name: String?, signature: String?,
                       superName: String?, interfaces: Array<out String>?) {
        owner = name
        isInterface = access and ACC_INTERFACE !== 0
        cv.visit(version, access, name, signature, superName, interfaces)
    }
}
