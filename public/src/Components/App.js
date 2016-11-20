import React from 'react';
import { Drawer, AppBar, MenuItem} from 'material-ui';
import { Router, Route, hashHistory, IndexRoute } from 'react-router';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme'  
import FontIcon from 'material-ui/FontIcon';  
import Content from './Content.jsx'
import {Link} from 'react-router';
import BodyA from './BodyA';
import BodyB from './BodyB';
import BodyC from './BodyC';
import Settings from 'material-ui/svg-icons/action/settings';
import Build from 'material-ui/svg-icons/action/build';
import QueryBuilder from 'material-ui/svg-icons/action/query-builder';
import ModeEdit from 'material-ui/svg-icons/editor/mode-edit';
import DeviceHub from 'material-ui/svg-icons/hardware/device-hub';

export default class App extends React.Component {

  constructor(props){
    super(props);
    this.state = {open:false};
  }

  getChildContext() {
    return {muiTheme: getMuiTheme(darkBaseTheme)};
  }

  
  render() {

      const styles = { 
        navbar: {top: "64px"},
        container: {
          margin: '80px 20px 20px 15px',
          paddingLeft: 256
        },
        iconStyles: {
          marginRight: 24,
          textAlign: "center"
        }
      }

        return (
            <MuiThemeProvider muiTheme={getMuiTheme(darkBaseTheme)}>
                <div>
                   <AppBar
                        ref="app-bar" 
                        showMenuIconButton={false}
                        title="Artemisia"/>
                  <Drawer
                    ref="drawer"
                    docked={true}
                    open={true}
                    containerStyle={styles.navbar}
                    onRequestChange={(open) => this.setState({open})}>
                        <MenuItem linkButton={true} href="/#/bodya" leftIcon={<Build/>}>
                            Profiles
                        </MenuItem>
                        <MenuItem linkButton={true} href="/#/bodyb" leftIcon={<ModeEdit/>}>
                            Jobs
                        </MenuItem>
                        <MenuItem linkButton={true} href="/#/bodyc" leftIcon={<DeviceHub/>}>
                            Workflow
                        </MenuItem>
                        <MenuItem linkButton={true} href="/#/bodya" leftIcon={<QueryBuilder/>}>Query</MenuItem>
                  </Drawer>
                      <div id="container" style={styles.container}>
                        <Router  history={hashHistory}>
                          <Route path="/" component={BodyA}/>
                          <Route path="/bodyb" component={BodyB}/>
                          <Route path="/bodyc" component={BodyC}/>
                          <Route path="/bodya" component={BodyA}/>
                        </Router>
                      </div>    
                  </div>
            </MuiThemeProvider>
        );
    }
}

App.childContextTypes = {
    muiTheme: React.PropTypes.object.isRequired,
};
