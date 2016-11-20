import { Router, Route, browserHistory, IndexRoute } from 'react-router';
import React from 'react';
import BodyA from './BodyA';
import BodyB from './BodyB';
import BodyC from './BodyC';

const Content = () => {
		 return (
	  <Router history={browserHistory}>
    		<Route path="/" component={BodyA} />
    		<Route path="/bingo" component={BodyB} />
    		<Route path="/charlie" component={BodyC} />
	  </Router>
	  );
}

export default Content;
