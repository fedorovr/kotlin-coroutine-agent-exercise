package agent

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by Roman Fedorov on 14.05.2017.
 */
class AddPrintTestMethodAdapter(mv: MethodVisitor?) : MethodVisitor(Opcodes.ASM4, mv) {
    private val TEST_DETECTED_NOTE = "Test detected"

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        mv.visitMaxs(Math.max(2, maxStack), maxLocals)
    }

    override fun visitCode() {
        mv.visitCode()
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        mv.visitLdcInsn(TEST_DETECTED_NOTE)
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
    }
}
