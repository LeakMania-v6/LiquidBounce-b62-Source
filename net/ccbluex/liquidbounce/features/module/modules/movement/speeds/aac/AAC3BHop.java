// 
// Decompiled by Procyon v0.5.36
// 

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.event.events.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;

public class AAC3BHop extends SpeedMode
{
    private boolean legitJump;
    
    public AAC3BHop() {
        super("AAC3BHop");
    }
    
    @Override
    public void onMotion() {
    }
    
    @Override
    public void onUpdate() {
    }
    
    @Override
    public void onMove(final MoveEvent event) {
    }
    
    @Override
    public void onTick() {
        AAC3BHop.mc.field_71428_T.field_74278_d = 1.0f;
        if (AAC3BHop.mc.field_71439_g.func_70090_H()) {
            return;
        }
        if (MovementUtils.isMoving()) {
            if (AAC3BHop.mc.field_71439_g.field_70122_E) {
                if (this.legitJump) {
                    AAC3BHop.mc.field_71439_g.func_70664_aZ();
                    this.legitJump = false;
                    return;
                }
                AAC3BHop.mc.field_71439_g.field_70181_x = 0.3852;
                AAC3BHop.mc.field_71439_g.field_70122_E = false;
                MovementUtils.strafe(0.374f);
            }
            else if (AAC3BHop.mc.field_71439_g.field_70181_x < 0.0) {
                AAC3BHop.mc.field_71439_g.field_71102_ce = 0.0201f;
                AAC3BHop.mc.field_71428_T.field_74278_d = 1.02f;
            }
            else {
                AAC3BHop.mc.field_71428_T.field_74278_d = 1.01f;
            }
        }
        else {
            this.legitJump = true;
            AAC3BHop.mc.field_71439_g.field_70159_w = 0.0;
            AAC3BHop.mc.field_71439_g.field_70179_y = 0.0;
        }
    }
}
