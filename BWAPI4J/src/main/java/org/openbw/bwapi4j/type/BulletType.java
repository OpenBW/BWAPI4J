package org.openbw.bwapi4j.type;

public enum BulletType {

	Acid_Spore(163),
	Anti_Matter_Missile(154),
	Arclite_Shock_Cannon_Hit(150), // 143 actually
	ATS_ATA_Laser_Battery(148),
	Burst_Lasers(149),
	C_10_Canister_Rifle_Hit(143),
	Consume(169),
	Corrosive_Acid_Shot(204),
	Dual_Photon_Blasters_Hit(152),
	EMP_Missile(151),
	Ensnare(170),
	Fragmentation_Grenade(155),
	Fusion_Cutter_Hit(141),
	Gauss_Rifle_Hit(142),
	Gemini_Missiles(144),
	Glave_Wurm(165),
	Halo_Rockets(202),
	Invisible(172),
	Longbolt_Missile(146),
	Melee(0),
	Needle_Spine_Hit(171),
	Neutron_Flare(206),
	None(-1),
	Optical_Flare_Grenade(201),
	Particle_Beam_Hit(153),
	Phase_Disruptor(159),
	Plague_Cloud(168),
	Psionic_Shockwave_Hit(156),
	Psionic_Storm(157),
	Pulse_Cannon(155),
	Queen_Spell_Carrier(167),
	Seeker_Spores(166),
	STA_STS_Cannon_Overlay(160),
	Subterranean_Spines(203),
	Sunken_Colony_Tentacle(161),
	Unknown(-1),
	Yamato_Gun(158);
	
	private int typeId;
	
	private BulletType(int typeId) {
	
		this.typeId = typeId;
	}
	
	public static BulletType valueOf(int typeId) {
		
		for (BulletType type : BulletType.values()) {
			
			if (type.typeId == typeId) {
				
				return type;
			}
		}
		return null;
	}
}