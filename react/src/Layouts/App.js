import React from 'react';
import { Drawer, AppBar, MenuItem} from 'material-ui';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme'
import Build from 'material-ui/svg-icons/action/build';
import Dashboard from 'material-ui/svg-icons/action/dashboard';
import Alarm from 'material-ui/svg-icons/action/alarm.js';
import ModeEdit from 'material-ui/svg-icons/editor/mode-edit';
import DeviceHub from 'material-ui/svg-icons/hardware/device-hub';
import DeveloperBoard from 'material-ui/svg-icons/hardware/developer-board.js';
import Content from './Content';


export default class App extends React.Component {


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
            appBar: {
                color: "#ffffff",
                backgroundColor: "#484848"
            }
        };

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
                        <MenuItem href="/#/dashboard" leftIcon={<Dashboard/>}>
                            Dashboard
                        </MenuItem>
                        <MenuItem href="/#/profiles" leftIcon={<Build/>}>
                            Profiles
                        </MenuItem>
                        <MenuItem href="/#/jobs" leftIcon={<ModeEdit/>}>
                            Jobs
                        </MenuItem>
                        <MenuItem href="/#/workflow" leftIcon={<DeviceHub/>}>
                            Workflows
                        </MenuItem>
                        <MenuItem href="/#/schedules" leftIcon={<Alarm/>}>
                            Schedules
                        </MenuItem>
                        <MenuItem href="/#/query" leftIcon={<DeveloperBoard/>}>
                            Query
                        </MenuItem>
                    </Drawer>
                    <Content />
                </div>
            </MuiThemeProvider>
        );
    }
}

App.childContextTypes = {
    muiTheme: React.PropTypes.object.isRequired
};
