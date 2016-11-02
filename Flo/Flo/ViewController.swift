//
//  ViewController.swift
//  Flo
//
//  Created by Patrick Salmas on 7/25/16.
//  Copyright (c) 2016 Pat Sal. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    var isGraphViewShowing = false
    
    @IBOutlet weak var counterView: Counter_View!
    @IBOutlet weak var counterLabel: UILabel!
    @IBOutlet weak var containerView: UIView!
    @IBOutlet weak var graphView: GraphView!
    @IBOutlet weak var averageWaterDrunk: UILabel!
    @IBOutlet weak var maxLabel: UILabel!
    @IBOutlet weak var medalView: MedalView!
    
    @IBAction func counterViewTap(gesture:UITapGestureRecognizer?) {
        if (isGraphViewShowing) {
            
            //hide graph
            UIView.transitionFromView(graphView, toView: counterView, duration: 1.0, options: UIViewAnimationOptions.TransitionFlipFromLeft | UIViewAnimationOptions.ShowHideTransitionViews, completion: nil)
        }
        else {
            
            //show Graph
            setupGraphDisplay()
            UIView.transitionFromView(counterView, toView: graphView, duration: 1.0, options: UIViewAnimationOptions.TransitionFlipFromRight | UIViewAnimationOptions.ShowHideTransitionViews, completion: nil)
            //setupGraphDisplay()
        }
        isGraphViewShowing = !isGraphViewShowing
    }
    
    @IBAction func btnPushButton(button: Push_ButtonView) {
        if button.isAddButton {
            counterView.counter++
        }
        else {
            if counterView.counter > 0 {
                counterView.counter--
            }
        }
        counterLabel.text = String(counterView.counter)
        
        if isGraphViewShowing {
            counterViewTap(nil)
        }
        
        checkTotal()
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        counterLabel.text = String(counterView.counter)
        // Do any additional setup after loading the view, typically from a nib.
        
        checkTotal()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    
    func setupGraphDisplay() {
        
        //Use 7 days for graph
        let noOfDays:Int = 7
        
        //replace last day with actual date
        graphView.graphPoints[graphView.graphPoints.count-1] = counterView.counter
        
        //redraw graph
        graphView.setNeedsDisplay()
        
        maxLabel.text = "\(maxElement(graphView.graphPoints))"
        
        //calculate average from graphPoints
        let average = graphView.graphPoints.reduce(0, combine: +) / graphView.graphPoints.count
        averageWaterDrunk.text = "\(average)"
        
        //SET UP LABELS FOR DAYS OF THE WEEK
    
        //get today's day number
        let dateFormatter = NSDateFormatter()
        let calendar = NSCalendar.currentCalendar()
        let componentOptions:NSCalendarUnit = .CalendarUnitWeekday
        let components = calendar.components(componentOptions, fromDate: NSDate())
        var weekday = components.weekday
        
        println("weekday = \(weekday)")
        
        let days = ["S","S","M","T","W","T","F"]
        
        //set up the name with correct day
        for i in reverse(1...days.count) {
            if let labelView = graphView.viewWithTag(i) as? UILabel {
                if weekday == 7 {
                    weekday = 0
                }
                labelView.text = days[weekday--]
                if weekday < 0 {
                    weekday = days.count - 1
                }
            }
        }
    }
    
    func checkTotal() {
        if counterView.counter >= 8 {
            medalView.showMedal(true)
        }
        else {
            medalView.showMedal(false)
        }
    }
   
}

