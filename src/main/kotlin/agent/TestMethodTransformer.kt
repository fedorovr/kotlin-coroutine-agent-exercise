package agent

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.lang.instrument.ClassFileTransformer
import java.security.ProtectionDomain

/**
 * Created by Roman Fedorov on 14.05.2017.
 */

class TestMethodTransformer : ClassFileTransformer {
    override fun transform(loader: ClassLoader?, className: String?, classBeingRedefined: Class<*>?,
                           protectionDomain: ProtectionDomain?, classfileBuffer: ByteArray?): ByteArray {
        val cr = ClassReader(classfileBuffer)
        val cw = ClassWriter(cr, 0)
        val apta = AddPrintTestAdapter(cw)
        cr.accept(apta, 0)
        return cw.toByteArray()
    }
}
