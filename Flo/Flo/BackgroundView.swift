//
//  BackgroundView.swift
//  Flo
//
//  Created by Patrick Salmas on 8/1/16.
//  Copyright (c) 2016 Pat Sal. All rights reserved.
//

import UIKit

class BackgroundView: UIView {
    
    @IBInspectable var lightColor: UIColor = UIColor.lightGrayColor()
    @IBInspectable var darkColor: UIColor = UIColor.yellowColor()
    @IBInspectable var patternSize:CGFloat = 200
    
    override func drawRect(rect: CGRect) {
        let context = UIGraphicsGetCurrentContext()
        
        CGContextSetFillColorWithColor(context, darkColor.CGColor)
        
        CGContextFillRect(context, rect)
        
        
        let drawSize = CGSize(width: patternSize, height: patternSize)
        
        UIGraphicsBeginImageContextWithOptions(drawSize, true, 0.0)
        let drawingContext = UIGraphicsGetCurrentContext()
        
        darkColor.setFill()
        CGContextFillRect(drawingContext, CGRectMake(0, 0, drawSize.width, drawSize.height))
        
        
        let trianglePath = UIBezierPath()
        //1
        trianglePath.moveToPoint(CGPoint(x:drawSize.width/2,
            y:0))
        //2
        trianglePath.addLineToPoint(CGPoint(x:0,
            y:drawSize.height/2))
        //3
        trianglePath.addLineToPoint(CGPoint(x:drawSize.width,
            y:drawSize.height/2))
        
        //4
        trianglePath.moveToPoint(CGPoint(x: 0,
            y: drawSize.height/2))
        //5
        trianglePath.addLineToPoint(CGPoint(x: drawSize.width/2,
            y: drawSize.height))
        //6
        trianglePath.addLineToPoint(CGPoint(x: 0,
            y: drawSize.height))
        
        //7
        trianglePath.moveToPoint(CGPoint(x: drawSize.width,
            y: drawSize.height/2))
        //8
        trianglePath.addLineToPoint(CGPoint(x:drawSize.width/2,
            y:drawSize.height))
        //9
        trianglePath.addLineToPoint(CGPoint(x: drawSize.width,
            y: drawSize.height))
        
        
        lightColor.setFill()
        trianglePath.fill()
        
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        UIColor(patternImage: image).setFill()
        CGContextFillRect(context, rect)
    
    }

    

}
