/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * define un enum para la creacion de colores para los diferentes
 * robots y tiendas disponibles en la simulacion, ademas de otros
 * colores como la arena, puertas y el camino
 *
 * @author juan diego patino munoz
 * @version 1
 */
package canvas;

import java.awt.Color;

public enum SColor
{
	sand   (new Color(227, 212, 168)),
	road   (new Color(139, 125, 107)),
	door   (new Color(0  ,   0,   0)),

	/*
	 *  _____________________
	 * < normal store styles >
	 *  ---------------------
	 *         \   ^__^
	 *          \  (oo)\_______
	 *             (__)\       )\/\
	 *                 ||----w |
	 *                 ||     ||
	 */
	NSFC1  (new Color(0  , 70 , 160)),
	NSRC1  (new Color(120, 180, 255)),

	NSFC2  (new Color(180, 30 , 30 )),
	NSRC2  (new Color(255, 140, 140)),

	NSFC3  (new Color(255, 100, 0  )),
	NSRC3  (new Color(255, 190, 130)),

	NSFC4  (new Color(100, 0  , 150)),
	NSRC4  (new Color(200, 150, 230)),

	NSFC5  (new Color(220, 40 , 120)),
	NSRC5  (new Color(255, 170, 210)),

	NSFC6  (new Color(100, 60 , 30 )),
	NSRC6  (new Color(200, 160, 120)),

	NSFC7  (new Color(200, 160, 0  )),
	NSRC7  (new Color(255, 240, 130)),

	NSFC8  (new Color(30 , 120, 30 )),
	NSRC8  (new Color(160, 230, 160)),

	NSFC9  (new Color(0  , 150, 160)),
	NSRC9  (new Color(140, 230, 240)),

	NSFC10 (new Color(230, 210, 170)),
	NSRC10 (new Color(255, 245, 220)),

	NSFC11 (new Color(120, 0  , 80 )),
	NSRC11 (new Color(230, 150, 200)),

	NSFC12 (new Color(0  , 100, 100)),
	NSRC12 (new Color(170, 220, 220)),

	NSFC13 (new Color(180, 90 , 10 )),
	NSRC13 (new Color(250, 200, 120)),

	NSFC14 (new Color(90 , 90 , 180)),
	NSRC14 (new Color(190, 190, 255)),

	NSFC15 (new Color(40 , 40 , 40 )),
	NSRC15 (new Color(160, 160, 160)),

	NSFC16 (new Color(100, 40 , 60 )),
	NSRC16 (new Color(210, 140, 160)),

	NSFC17 (new Color(80 , 130, 60 )),
	NSRC17 (new Color(180, 230, 160)),

	/*
	 *  ___________________
	 * < auto store styles >
	 *  -------------------
	 *         \   ^__^
	 *          \  (oo)\_______
	 *             (__)\       )\/\
	 *                 ||----w |
	 *                 ||     ||
	 */
	ASC1  (new Color(0xb5, 0xc2, 0xb7)),
	ASC2  (new Color(0x8c, 0x93, 0xa8)),
	ASC3  (new Color(0x62, 0x46, 0x6b)),
	ASC4  (new Color(0x45, 0x36, 0x4b)),
	ASC5  (new Color(0x2d, 0x23, 0x27)),
	ASC6  (new Color(0xf4, 0x33, 0xab)),
	ASC7  (new Color(0xcb, 0x04, 0xa5)),
	ASC8  (new Color(0x93, 0x46, 0x83)),
	ASC9  (new Color(0x65, 0x33, 0x4d)),
	ASC10 (new Color(0x2d, 0x11, 0x15)),
	ASC11 (new Color(0xd5, 0xb9, 0x42)),
	ASC12 (new Color(0xd9, 0xd3, 0x75)),
	ASC13 (new Color(0xbf, 0xcb, 0xc2)),
	ASC14 (new Color(0xb4, 0x8e, 0xae)),
	ASC15 (new Color(0x64, 0x6e, 0x68)),
	ASC16 (new Color(0x3a, 0xb7, 0x95)),
	ASC17 (new Color(0x86, 0xba, 0xa1)),

	/*
	 *  _______________________
	 * < fighter  store styles >
	 *  -----------------------
	 *         \   ^__^
	 *          \  (oo)\_______
	 *             (__)\       )\/\
	 *                 ||----w |
	 *                 ||     ||
	 */
	FSC1  (new Color(230, 230, 220)),
	FSC2  (new Color(215, 210, 200)),
	FSC3  (new Color(245, 240, 230)),
	FSC4  (new Color(210, 210, 210)),
	FSC5  (new Color(225, 215, 205)),
	FSC6  (new Color(200, 190, 180)),
	FSC7  (new Color(235, 225, 200)),
	FSC8  (new Color(210, 190, 170)),
	FSC9  (new Color(240, 230, 210)),
	FSC10 (new Color(190, 200, 190)),
	FSC11 (new Color(200, 210, 200)),
	FSC12 (new Color(190, 200, 210)),
	FSC13 (new Color(210, 220, 230)),
	FSC14 (new Color(220, 225, 230)),
	FSC15 (new Color(215, 200, 180)),
	FSC16 (new Color(200, 185, 160)),
	FSC17 (new Color(210, 210, 210)),

	/*
	 *  __________________________
	 * < all robots styles styles >
	 *  --------------------------
	 *         \   ^__^
	 *          \  (oo)\_______
	 *             (__)\       )\/\
	 *                 ||----w |
	 *                 ||     ||
	 */
	RC1  (new Color(180, 180, 170)),
	RC2  (new Color(160, 150, 140)),
	RC3  (new Color(200, 190, 180)),
	RC4  (new Color(150, 150, 150)),
	RC5  (new Color(140, 130, 120)),
	RC6  (new Color(110, 100, 90 )),
	RC7  (new Color(170, 160, 140)),
	RC8  (new Color(130, 110, 90 )),
	RC9  (new Color(190, 180, 160)),
	RC10 (new Color(100, 110, 100)),
	RC11 (new Color(120, 130, 120)),
	RC12 (new Color(110, 120, 130)),
	RC13 (new Color(130, 140, 150)),
	RC14 (new Color(160, 165, 170)),
	RC15 (new Color(120, 100, 80 )),
	RC16 (new Color(100, 90,  70 )),
	RC17 (new Color(110, 110, 110));

	private final Color self;

	SColor (final Color color) { this.self = color; }
	public Color getcolor ()   { return this.self; }
}
