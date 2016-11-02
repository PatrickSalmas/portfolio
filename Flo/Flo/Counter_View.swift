//
//  Counter_View.swift
//  Flo
//
//  Created by Patrick Salmas on 7/26/16.
//  Copyright (c) 2016 Pat Sal. All rights reserved.
//

import UIKit

let NoOfGlasses = 8
let pi:CGFloat = CGFloat(M_PI)

@IBDesignable class Counter_View: UIView {

    @IBInspectable var counter: Int = 5 {
        didSet {
            if counter <= NoOfGlasses {
                setNeedsDisplay()
            }
        }
    }
    @IBInspectable var outlineColor: UIColor = UIColor.blueColor()
    @IBInspectable var counterColor: UIColor = UIColor.orangeColor()
    
    /*
    // Only override drawRect: if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func drawRect(rect: CGRect) {
        // Drawing code
    }
    */
    override func drawRect(rect: CGRect) {
        //COUNTER ARC
        let center = CGPoint(x:bounds.width/2, y:bounds.height/2)
        let radius: CGFloat = max(bounds.width, bounds.height)
        
        let arcWidth: CGFloat = 76
        
        let startAngle: CGFloat = 3 * CGFloat(M_PI) / 4
        let endAngle: CGFloat = CGFloat(M_PI) / 4
        
        var path = UIBezierPath(arcCenter: center, radius: radius/2  - arcWidth/2, startAngle: startAngle, endAngle: endAngle, clockwise: true)
        
        path.lineWidth = arcWidth
        counterColor.setStroke()
        path.stroke()
        
        
        //OUTLINE ARC
        //calculate difference between the two angles
        let angleDifference: CGFloat = 2 * CGFloat(M_PI) - startAngle + endAngle
        
        //calculate the arc for each single glass
        let arcLengthPerGlass = angleDifference / CGFloat(NoOfGlasses)
        
        //multiply out by the actual glasses drunk
        let outlineEndAngle = arcLengthPerGlass * CGFloat(counter) + startAngle
        
        //draw the outer arc
        var outlinePath = UIBezierPath(arcCenter: center,
            radius: bounds.width/2 - 2.5, startAngle: startAngle, endAngle: outlineEndAngle, clockwise: true)
        
        //draw the inner arc
        outlinePath.addArcWithCenter(center, radius: bounds.width/2 - arcWidth + 2.5, startAngle: outlineEndAngle, endAngle: startAngle, clockwise: false)
        
        //close the path
        outlinePath.closePath()
        
        outlineColor.setStroke()
        outlinePath.lineWidth = 5.0
        outlinePath.stroke()
        
        //COUNTER VIEW MARKERS
        let context = UIGraphicsGetCurrentContext()
        
        //save original state
        CGContextSaveGState(context)
        outlineColor.setFill()
        
        let markerWidth:CGFloat = 5.0
        let markerSize:CGFloat = 10.0
        
        //set marker of rectanlge positioned top left
        var markerPath = UIBezierPath(rect: CGRect(x: -markerWidth/2, y: 0, width: markerWidth, height: markerSize))
        
        //move top left of context to the previous center position
        CGContextTranslateCTM(context, rect.width/2, rect.height/2)
        
        for i in 1...NoOfGlasses {
            //save the centered context
            CGContextSaveGState(context)
            
            //calculate the rotation angle
            var angle = arcLengthPerGlass * CGFloat(i) + startAngle - CGFloat(M_PI)/2
            
            //rotate and translate
            CGContextRotateCTM(context, angle)
            CGContextTranslateCTM(context, 0, rect.height/2 - markerSize)
            
            //fill the marker rectangle
            markerPath.fill()
            
            //restore the centered context for the next rotate
            CGContextRestoreGState(context)
        }
        
        //restore the original state
        CGContextRestoreGState(context)
        
    }
    
    

}
