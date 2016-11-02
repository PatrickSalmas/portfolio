//
//  GraphView.swift
//  Flo
//
//  Created by Patrick Salmas on 7/27/16.
//  Copyright (c) 2016 Pat Sal. All rights reserved.
//

import UIKit

@IBDesignable class GraphView: UIView {

    /*
    // Only override drawRect: if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func drawRect(rect: CGRect) {
        // Drawing code
    }
    */
    //weekly sample points
    var graphPoints: [Int] = [4, 2, 6, 4, 5, 8, 3]
    
    //set the properties for the gradient
    @IBInspectable var startColor: UIColor = UIColor.redColor()
    @IBInspectable var endColor: UIColor = UIColor.greenColor()
    
    override func drawRect(rect: CGRect) {
        
        let width = rect.width
        let height = rect.height
        
        var path = UIBezierPath(roundedRect: rect, byRoundingCorners: UIRectCorner.AllCorners, cornerRadii:CGSize(width: 8.0, height: 8.0))
        path.addClip()
        
        //get the current context
        let context = UIGraphicsGetCurrentContext()
        let colors = [startColor.CGColor, endColor.CGColor]
        
        //set up the color space
        let colorSpace = CGColorSpaceCreateDeviceRGB()
        
        //set up the color stops
        let colorLocations:[CGFloat] = [0.0, 1.0]
        
        //create the gradient
        let gradient = CGGradientCreateWithColors(colorSpace, colors, colorLocations)
        
        //draw the gradient
        var startPoint = CGPoint.zeroPoint
        var endPoint = CGPoint(x:0, y:self.bounds.height)
        CGContextDrawLinearGradient(context,gradient,startPoint,endPoint,0)
        
        //calculate the x point
        let margin: CGFloat = 20.0
        var columnXPoint = { (column:Int) -> CGFloat in
            //calulate gap between points
            let spacer = (width - margin*2 - 4) / CGFloat((self.graphPoints.count - 1))
            var x:CGFloat = CGFloat(column) * spacer
            x += margin + 2
            return x
        }
        
        //caculate the y point
        let topBorder: CGFloat = 60
        let bottomBorder:CGFloat = 50
        let graphHeight = height - topBorder - bottomBorder
        let maxValue = maxElement(graphPoints)
        var columnYPoint = { (graphPoints:Int) -> CGFloat in
            var y:CGFloat = CGFloat(graphPoints) / CGFloat(maxValue) * graphHeight
            y = graphHeight + topBorder - y //Flip the graph
            return y
        }
        
        //draw the line graph
        UIColor.whiteColor().setFill()
        UIColor.whiteColor().setStroke()
        
        //set up the points line
        var graphPath = UIBezierPath()
        //go to start of line
        graphPath.moveToPoint(CGPoint(x:columnXPoint(0), y:columnYPoint(graphPoints[0])))
        
        //add points for each item in the graphPoints array
        //at the correct (x, y) for the point
        for i in 1..<graphPoints.count {
            let nextPoint = CGPoint(x:columnXPoint(i), y:columnYPoint(graphPoints[i]))
            graphPath.addLineToPoint(nextPoint)
        }
        
        //CREATE THE CLIPPING PATH FOR THE GRAPH GRADIENT
        
        //save the state of the context (commented out for now)
        CGContextSaveGState(context)
        
        //make a copy of the path
        var clippingPath = graphPath.copy() as UIBezierPath
        
        clippingPath.addLineToPoint(CGPoint(x: columnXPoint(graphPoints.count - 1), y:height))
        clippingPath.addLineToPoint(CGPoint(x:columnXPoint(0),y:height))
        clippingPath.closePath()
        
        //add clipping path to the context
        clippingPath.addClip()
        
        let highestYPoint = columnYPoint(maxValue)
        startPoint = CGPoint(x:margin, y:highestYPoint)
        endPoint = CGPoint(x:margin, y:self.bounds.height)
        
        CGContextDrawLinearGradient(context, gradient, startPoint, endPoint, 0)
        
        graphPath.stroke()
        CGContextRestoreGState(context)
        
        //Draw the circles on top of graph stroke
        for i in 0..<graphPoints.count {
            var point = CGPoint(x:columnXPoint(i), y:columnYPoint(graphPoints[i]))
            point.x -= 5.0/2
            point.y -= 5.0/2
            let circle = UIBezierPath(ovalInRect: CGRect(origin: point, size: CGSize(width: 5.0, height: 5.0)))
            circle.fill()
            
        }
        //DRAW HORIZONTAL GRAPH LINES
        var linePath = UIBezierPath()
        
        //top line
        linePath.moveToPoint(CGPoint(x:margin, y:topBorder))
        linePath.addLineToPoint(CGPoint(x: width - margin, y:topBorder))
        
        //center line
        linePath.moveToPoint(CGPoint(x:margin, y: graphHeight/2 + topBorder))
        linePath.addLineToPoint(CGPoint(x:width - margin, y:graphHeight/2 + topBorder))
        
        //bottom line
        linePath.moveToPoint(CGPoint(x:margin, y:height - bottomBorder))
        linePath.moveToPoint(CGPoint(x:width - margin, y:height - bottomBorder))
        
        let color = UIColor(white: 1.0, alpha: 0.3)
        color.setStroke()
       
        linePath.lineWidth = 1.0
        linePath.stroke()
        
    }

}
