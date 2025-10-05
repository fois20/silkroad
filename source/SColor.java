/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * @author  hever barrera batero ; juan diego patino munoz
 * @version 1
 *
 * Esta clase provee diferentes colores usando la clase java.awt.Color,
 * con el proposito de hacer el simulador mas vistoso y menos aburrido.
 */

import java.awt.Color;

public enum SColor
{
	sand  (new Color(227, 212, 168)),
	road  (new Color(139, 125, 107)),

	c1f  (new Color(228, 214, 170)),
	c1r  (new Color(150, 120, 90) ),

	c2f  (new Color(210, 190, 160)),
	c2r  (new Color(110, 90 , 70 )),

	c3f  (new Color(193, 154, 107)),
	c3r  (new Color(120, 80 , 50 )),

	c4f  (new Color(180, 140, 100)),
	c4r  (new Color(90 , 60 , 40 )),

	c5f  (new Color(240, 235, 220)),
	c5r  (new Color(160, 140, 120)),

	c6f  (new Color(235, 230, 210)),
	c6r  (new Color(120, 100, 80 )),

	c7f  (new Color(200, 200, 200)),
	c7r  (new Color(100, 100, 100)),

	c8f  (new Color(160, 160, 160)),
	c8r  (new Color(60 , 60 , 60 )),

	c9f  (new Color(150, 140, 130)),
	c9r  (new Color(50 , 50 , 50 )),

	c10f (new Color(120, 110, 100)),
	c10r (new Color(30 , 30 , 30 )),

	c11f (new Color(245, 220, 200)),
	c11r (new Color(160, 100, 70 )),

	c12f (new Color(230, 210, 200)),
	c12r (new Color(120, 80 , 60 )),

	c13f (new Color(210, 225, 220)),
	c13r (new Color(90 , 110, 100)),

	c14f (new Color(200, 210, 230)),
	c14r (new Color(80 , 90 , 120)),

	c15f (new Color(185, 175, 140)),
	c15r (new Color(100, 90 , 60 )),

	c16f (new Color(170, 160, 120)),
	c16r (new Color(70 , 60 , 40 )),

	c17f (new Color(160, 120, 90 )),
	c17r (new Color(60 , 40 , 30 )),

	r1  (new Color(228, 214, 170)),
	r2  (new Color(193, 154, 107)),
	r3  (new Color(245, 220, 200)),
	r4  (new Color(200, 200, 200)),
	r5  (new Color(160, 120, 90)),
	r6  (new Color(130, 65,  45 )),
	r7  (new Color(230, 200, 70 )),
	r8  (new Color(214, 93,  14 )),
	r9  (new Color(255, 140, 0  )),
	r10 (new Color(34,  139, 34 )),
	r11 (new Color(60,  179, 113)),
	r12 (new Color(72,  160, 200)),
	r13 (new Color(100, 149, 237)),
	r14 (new Color(200, 210, 230)),
	r15 (new Color(184, 115, 51 )),
	r16 (new Color(210, 105, 30 )),
	r17 (new Color(100, 100, 100));

	private final Color self;

	SColor (final Color color) { this.self = color; }
	public Color getcolor ()   { return this.self; }
}
