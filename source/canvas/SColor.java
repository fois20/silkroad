/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * define gran variedad de colores para la simulacion; se definen
 * 17 colores para techos, 17 colores para fachadas, 17 colores
 * para robots, el color de la arena y el color del terreno por donde
 * los robots caminan
 *
 * @author juan diego patino munoz ; hever barrera batero
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
	ASC1  (new Color(255, 248, 220)),
	ASC2  (new Color(255, 239, 204)),
	ASC3  (new Color(250, 240, 190)),
	ASC4  (new Color(255, 235, 205)),
	ASC5  (new Color(255, 228, 196)),
	ASC6  (new Color(245, 222, 179)),
	ASC7  (new Color(240, 224, 200)),
	ASC8  (new Color(238, 214, 175)),
	ASC9  (new Color(233, 206, 160)),
	ASC10 (new Color(229, 198, 152)),
	ASC11 (new Color(222, 184, 135)),
	ASC12 (new Color(218, 165, 105)),
	ASC13 (new Color(210, 180, 140)),
	ASC14 (new Color(205, 170, 125)),
	ASC15 (new Color(194, 178, 128)),
	ASC16 (new Color(189, 183, 107)),
	ASC17 (new Color(176, 165, 120)),

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

	normalRobotC1  (new Color(180, 180, 170)),
	normalRobotC2  (new Color(160, 150, 140)),
	normalRobotC3  (new Color(200, 190, 180)),
	normalRobotC4  (new Color(150, 150, 150)),
	normalRobotC5  (new Color(140, 130, 120)),
	normalRobotC6  (new Color(110, 100, 90 )),
	normalRobotC7  (new Color(170, 160, 140)),
	normalRobotC8  (new Color(130, 110, 90 )),
	normalRobotC9  (new Color(190, 180, 160)),
	normalRobotC10 (new Color(100, 110, 100)),
	normalRobotC11 (new Color(120, 130, 120)),
	normalRobotC12 (new Color(110, 120, 130)),
	normalRobotC13 (new Color(130, 140, 150)),
	normalRobotC14 (new Color(160, 165, 170)),
	normalRobotC15 (new Color(120, 100, 80 )),
	normalRobotC16 (new Color(100, 90,  70 )),
	normalRobotC17 (new Color(110, 110, 110));

	private final Color self;

	SColor (final Color color) { this.self = color; }
	public Color getcolor ()   { return this.self; }
}
