import Jobs from './Jobs';
import Workflow from './Workflow';
import Profiles from './Profiles';
import Query from './Query';
import Dashboard from './Dashboard';
import Schedule from './Schedule'
import { Router, Route, hashHistory } from 'react-router';
import React from 'react';

const Content = () => {

    const styles = {
            margin: '80px 20px 20px 15px',
            paddingLeft: 256
    };

    return (
        <div id="container" style={styles}>
            <Router  history={hashHistory}>
                <Route path="/" component={Dashboard}/>
                <Route path="/dashboard" component={Dashboard}/>
                <Route path="/jobs" component={Jobs}/>
                <Route path="/workflow" component={Workflow}/>
                <Route path="/profiles" component={Profiles}/>
                <Route path="/schedules" component={Schedule}/>
                <Route path="/query" component={Query}/>
            </Router>
        </div>
    )
};

export default Content;