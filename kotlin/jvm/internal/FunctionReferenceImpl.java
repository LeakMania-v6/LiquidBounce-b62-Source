// 
// Decompiled by Procyon v0.5.36
// 

package kotlin.jvm.internal;

import kotlin.reflect.KDeclarationContainer;

public class FunctionReferenceImpl extends FunctionReference
{
    private final KDeclarationContainer owner;
    private final String name;
    private final String signature;
    
    public FunctionReferenceImpl(final int arity, final KDeclarationContainer owner, final String name, final String signature) {
        super(arity);
        this.owner = owner;
        this.name = name;
        this.signature = signature;
    }
    
    @Override
    public KDeclarationContainer getOwner() {
        return this.owner;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getSignature() {
        return this.signature;
    }
}
