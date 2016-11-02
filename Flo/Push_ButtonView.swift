//
//  Push_ButtonView.swift
//  Flo
//
//  Created by Patrick Salmas on 7/25/16.
//  Copyright (c) 2016 Pat Sal. All rights reserved.
//

import UIKit

@IBDesignable

class Push_ButtonView: UIButton {
    
    @IBInspectable var fillColor: UIColor = UIColor.greenColor()
    @IBInspectable var isAddButton: Bool = true

    /*
    // Only override drawRect: if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func drawRect(rect: CGRect) {
        // Drawing code
    }
    */
    
    override func drawRect(rect: CGRect) {
        var path = UIBezierPath(ovalInRect: rect)
        //UIColor.blueColor().setFill()
        fillColor.setFill()
        path.fill()
        
        //set up the width and height variables
        //for the horizontal stroke
        let plusWidth: CGFloat = 3.0
        let plusLength: CGFloat = min(bounds.width, bounds.height) * 0.5
        
        //create the path
        var plusPath = UIBezierPath()
        
        //set the path's line width of the height of the stroke
        plusPath.lineWidth = plusWidth
        
        //move the initial point of the path
        //to the start of the horizontal stroke
        plusPath.moveToPoint(CGPoint(x:bounds.width/2 - plusLength/2, y:bounds.height/2))
        
        //add a point to the path at the end of the stroke
        plusPath.addLineToPoint(CGPoint(x:bounds.width/2 + plusLength/2, y:bounds.height/2))
        
        //set the stroke color
        UIColor.whiteColor().setStroke()
        
        //draw the stroke
        plusPath.stroke()
        
        
        //NOW MY TRY
        if isAddButton{
           plusPath.moveToPoint(CGPoint(x:bounds.width/2, y:bounds.height/2 - plusLength/2))
        
           plusPath.addLineToPoint(CGPoint(x:bounds.width/2, y:bounds.height/2 + plusLength/2))
        
           plusPath.stroke()
        }
    }

}
