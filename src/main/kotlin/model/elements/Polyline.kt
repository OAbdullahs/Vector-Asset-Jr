package model.elements

import model.PathConverter
import model.Point
import model.SvgElement
import model.Transform
import model.style.Style

data class Polyline(
    val points: List<Point>,
    override var style: Style = Style(),
    override var transform: List<Transform>
) : SvgElement(style, transform), PathConverter {

    companion object {
        const val NodeName = "polyline"
    }

    override fun toPath(): Path {
        return Path(points.mapIndexed { index, point ->
            if (index == 0) {
                Path.Action.Move(point.x, point.y)
            } else {
                Path.Action.LineTo(point.x, point.y)
            }
        } + Path.Action.Close, style, transform).getTransformedPath()
    }

    override fun toXml(): String {
        return "<${Polygon.NodeName} points=\"${
            points.joinToString {
                "${it.x},${it.y},"
            }.removeSuffix(",")
        }\" />"
    }
}