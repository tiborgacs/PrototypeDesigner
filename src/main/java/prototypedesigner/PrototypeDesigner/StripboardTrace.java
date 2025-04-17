package prototypedesigner.PrototypeDesigner;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prototypedesigner.PrototypeDesigner.components.DrawableOnStripboard;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude
@JsonIdentityInfo(property = "identifier", generator = ObjectIdGenerators.PropertyGenerator.class)
public class StripboardTrace implements DrawableOnStripboard {
	
	private int x;
	private int y;
	private int w;

	private String identifier;

	public StripboardTrace(int x, int y, int length) {
		this.x = x;
		this.y = y;
		this.w = length;
		identifier = "str#" + x + ":" + y;
	}
	
	public StripboardTrace cutAt(int x, int y) {
		if (y != this.y) return null;
		if (x >= this.x && x < this.x + this.w) {
			if (x == this.x) {
				this.x += 1;
				this.w -= 1;
				identifier = "str#" + this.x + ":" + this.y;
				return null;
			} else if (x == this.x + this.w - 1) {
				this.w -= 1;
				return null;
			} else {
				StripboardTrace remainder = new StripboardTrace(this.x, this.y, x-this.x);
				this.w = this.x + this.w - x - 1;
				this.x = x + 1;
				identifier = "str#" + this.x + ":" + this.y;
				return remainder;
			}
		}
		return null;
	}

	@Override
	public void drawOnStripboard(@SuppressWarnings("exports") GraphicsContext context) {
		context.setGlobalAlpha(1.0);
		context.setFill(Color.CHOCOLATE); // TODO: highlighting
		context.fillRect(x*24+1, y*24+1, w*24-2, 22);
		context.setFill(Color.BROWN);
		context.fillRect(x*24+1, y*24+2, w*24-2, 20);
		for (int x = this.x/24; x < w; x++) {
			context.setFill(Color.WHITE);
			context.fillOval((this.x+x)*24+9, y*24+9, 6, 6);
		}
	}

}
