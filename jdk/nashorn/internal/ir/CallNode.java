// 
// Decompiled by Procyon v0.5.36
// 

package jdk.nashorn.internal.ir;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
import jdk.nashorn.internal.ir.annotations.Ignore;
import jdk.nashorn.internal.codegen.types.Type;
import java.util.List;
import jdk.nashorn.internal.ir.annotations.Immutable;

@Immutable
public final class CallNode extends LexicalContextExpression implements Optimistic
{
    private static final long serialVersionUID = 1L;
    private final Expression function;
    private final List<Expression> args;
    private static final int IS_NEW = 1;
    private static final int IS_APPLY_TO_CALL = 2;
    private final int flags;
    private final int lineNumber;
    private final int programPoint;
    private final Type optimisticType;
    @Ignore
    private final EvalArgs evalArgs;
    
    public CallNode(final int lineNumber, final long token, final int finish, final Expression function, final List<Expression> args, final boolean isNew) {
        super(token, finish);
        this.function = function;
        this.args = args;
        this.flags = (isNew ? 1 : 0);
        this.evalArgs = null;
        this.lineNumber = lineNumber;
        this.programPoint = -1;
        this.optimisticType = null;
    }
    
    private CallNode(final CallNode callNode, final Expression function, final List<Expression> args, final int flags, final Type optimisticType, final EvalArgs evalArgs, final int programPoint) {
        super(callNode);
        this.lineNumber = callNode.lineNumber;
        this.function = function;
        this.args = args;
        this.flags = flags;
        this.evalArgs = evalArgs;
        this.programPoint = programPoint;
        this.optimisticType = optimisticType;
    }
    
    public int getLineNumber() {
        return this.lineNumber;
    }
    
    @Override
    public Type getType() {
        return (this.optimisticType == null) ? Type.OBJECT : this.optimisticType;
    }
    
    @Override
    public Optimistic setType(final Type optimisticType) {
        if (this.optimisticType == optimisticType) {
            return this;
        }
        return new CallNode(this, this.function, this.args, this.flags, optimisticType, this.evalArgs, this.programPoint);
    }
    
    @Override
    public Node accept(final LexicalContext lc, final NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterCallNode(this)) {
            final CallNode newCallNode = (CallNode)visitor.leaveCallNode(this.setFunction((Expression)this.function.accept(visitor)).setArgs(Node.accept(visitor, this.args)).setEvalArgs((this.evalArgs == null) ? null : this.evalArgs.setArgs(Node.accept(visitor, this.evalArgs.getArgs()))));
            if (this != newCallNode) {
                return Node.replaceInLexicalContext(lc, this, newCallNode);
            }
        }
        return this;
    }
    
    @Override
    public void toString(final StringBuilder sb, final boolean printType) {
        if (printType) {
            this.optimisticTypeToString(sb);
        }
        final StringBuilder fsb = new StringBuilder();
        this.function.toString(fsb, printType);
        if (this.isApplyToCall()) {
            sb.append(fsb.toString().replace("apply", "[apply => call]"));
        }
        else {
            sb.append((CharSequence)fsb);
        }
        sb.append('(');
        boolean first = true;
        for (final Node arg : this.args) {
            if (!first) {
                sb.append(", ");
            }
            else {
                first = false;
            }
            arg.toString(sb, printType);
        }
        sb.append(')');
    }
    
    public List<Expression> getArgs() {
        return Collections.unmodifiableList((List<? extends Expression>)this.args);
    }
    
    public CallNode setArgs(final List<Expression> args) {
        if (this.args == args) {
            return this;
        }
        return new CallNode(this, this.function, args, this.flags, this.optimisticType, this.evalArgs, this.programPoint);
    }
    
    public EvalArgs getEvalArgs() {
        return this.evalArgs;
    }
    
    public CallNode setEvalArgs(final EvalArgs evalArgs) {
        if (this.evalArgs == evalArgs) {
            return this;
        }
        return new CallNode(this, this.function, this.args, this.flags, this.optimisticType, evalArgs, this.programPoint);
    }
    
    public boolean isEval() {
        return this.evalArgs != null;
    }
    
    public boolean isApplyToCall() {
        return (this.flags & 0x2) != 0x0;
    }
    
    public CallNode setIsApplyToCall() {
        return this.setFlags(this.flags | 0x2);
    }
    
    public Expression getFunction() {
        return this.function;
    }
    
    public CallNode setFunction(final Expression function) {
        if (this.function == function) {
            return this;
        }
        return new CallNode(this, function, this.args, this.flags, this.optimisticType, this.evalArgs, this.programPoint);
    }
    
    public boolean isNew() {
        return (this.flags & 0x1) != 0x0;
    }
    
    private CallNode setFlags(final int flags) {
        if (this.flags == flags) {
            return this;
        }
        return new CallNode(this, this.function, this.args, flags, this.optimisticType, this.evalArgs, this.programPoint);
    }
    
    @Override
    public int getProgramPoint() {
        return this.programPoint;
    }
    
    @Override
    public CallNode setProgramPoint(final int programPoint) {
        if (this.programPoint == programPoint) {
            return this;
        }
        return new CallNode(this, this.function, this.args, this.flags, this.optimisticType, this.evalArgs, programPoint);
    }
    
    @Override
    public Type getMostOptimisticType() {
        return Type.INT;
    }
    
    @Override
    public Type getMostPessimisticType() {
        return Type.OBJECT;
    }
    
    @Override
    public boolean canBeOptimistic() {
        return true;
    }
    
    public static class EvalArgs implements Serializable
    {
        private static final long serialVersionUID = 1L;
        private final List<Expression> args;
        private final String location;
        
        public EvalArgs(final List<Expression> args, final String location) {
            this.args = args;
            this.location = location;
        }
        
        public List<Expression> getArgs() {
            return Collections.unmodifiableList((List<? extends Expression>)this.args);
        }
        
        private EvalArgs setArgs(final List<Expression> args) {
            if (this.args == args) {
                return this;
            }
            return new EvalArgs(args, this.location);
        }
        
        public String getLocation() {
            return this.location;
        }
    }
}
