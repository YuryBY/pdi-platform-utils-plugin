/*!
* Copyright 2002 - 2014 Webdetails, a Pentaho company.  All rights reserved.
*
* This software was developed by Webdetails and is provided under the terms
* of the Mozilla Public License, Version 2.0, or any later version. You may not use
* this file except in compliance with the license. If you need a copy of the license,
* please go to  http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
*
* Software distributed under the Mozilla Public License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
* the license for the specific language governing your rights and limitations.
*/

package pt.webdetails.di.baserver.utils;

import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.annotations.Step;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.metastore.api.IMetaStore;
import org.w3c.dom.Node;

import java.util.List;

/**
 * @author Marco Vala
 */
@Step( id = "CallEndpointStep",
  name = "CallEndpointMeta.Name",
  image = "icons/callendpoint.png",
  description = "CallEndpointMeta.Description",
  i18nPackageName = "pt.webdetails.di.baserverutils",
  categoryDescription = "BAServerUtils.Category",
  isSeparateClassLoaderNeeded = true )
public class CallEndpointMeta extends BaseStepMeta implements StepMetaInterface {
  private static Class<?> PKG = CallEndpointMeta.class; // for i18n purposes, needed by Translator2!!

  private String serverURL;
  private String username;
  private String password;
  private boolean bypassAuthCheck;
  private String module;
  private boolean isModuleField = false;
  private String service;
  private boolean isServiceField = false;
  private String resultField = "";
  private String statusCodeField = "";
  private String responseTimeField = "";
  private String[] fieldName;
  private String[] parameter;
  private String[] defaultValue;


  public CallEndpointMeta() {
    super(); // allocate BaseStepMeta
    setDefault();
  }

  public StepInterface getStep( StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr,
                                TransMeta transMeta, Trans trans ) {
    return new CallEndpointStep( stepMeta, stepDataInterface, cnr, transMeta, trans );
  }

  public StepDataInterface getStepData() {
    return new CallEndpointData();
  }

  public StepDialogInterface getDialog( Shell shell, StepMetaInterface meta, TransMeta transMeta, String name ) {
    return new CallEndpointDialog( shell, meta, transMeta, name );
  }


  public String getServerURL() {
    return this.serverURL;
  }

  public void setServerURL( String serverURL ) {
    this.serverURL = serverURL;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername( String username ) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword( String password ) {
    this.password = password;
  }

  public boolean isBypassingAuthCheck() {
    return this.bypassAuthCheck;
  }

  public void setBypassAuthCheck( boolean bypassAuthCheck ) {
    this.bypassAuthCheck = bypassAuthCheck;
  }

  public String getModule() {
    return this.module;
  }

  public void setModule( String module ) {
    this.module = module;
  }

  public boolean isModuleField() {
    return this.isModuleField;
  }

  public void setIsModuleField( boolean isModuleField ) {
    this.isModuleField = isModuleField;
  }

  public String getService() {
    return this.service;
  }

  public void setService( String service ) {
    this.service = service;
  }

  public boolean isServiceField() {
    return this.isServiceField;
  }

  public void setIsServiceField( boolean isServiceField ) {
    this.isServiceField = isServiceField;
  }

  public String getResultField() {
    return this.resultField;
  }

  public void setResultField( String resultField ) {
    this.resultField = resultField;
  }

  public String getStatusCodeField() {
    return this.statusCodeField;
  }

  public void setStatusCodeField( String statusCodeField ) {
    this.statusCodeField = statusCodeField;
  }

  public String getResponseTimeField() {
    return this.responseTimeField;
  }

  public void setResponseTimeField( String responseTimeField ) {
    this.responseTimeField = responseTimeField;
  }

  public String[] getFieldName() {
    return this.fieldName;
  }

  public void setFieldName( String[] fieldName ) {
    this.fieldName = fieldName;
  }

  public String[] getParameter() {
    return this.parameter;
  }

  public void setParameter( String[] fieldValue ) {
    this.parameter = fieldValue;
  }

  public String[] getDefaultValue() {
    return this.defaultValue;
  }

  public void setDefaultValue( String[] defaultValue ) {
    this.defaultValue = defaultValue;
  }


  public void allocate( int count ) {
    this.fieldName = new String[ count ];
    this.parameter = new String[ count ];
    this.defaultValue = new String[ count ];
  }

  public void setDefault() {
    this.serverURL = "";
    this.username = "";
    this.password = "";
    this.bypassAuthCheck = false;
    this.module = "";
    this.isModuleField = false;
    this.service = "";
    this.isServiceField = false;
    this.resultField = "";
    this.statusCodeField = "";
    this.responseTimeField = "";
    allocate( 0 );
  }

  public Object clone() {
    CallEndpointMeta clone = (CallEndpointMeta) super.clone();
    clone.serverURL = this.serverURL;
    clone.username = this.username;
    clone.password = this.password;
    clone.bypassAuthCheck = this.bypassAuthCheck;
    clone.module = this.module;
    clone.isModuleField = this.isModuleField;
    clone.service = this.service;
    clone.isServiceField = this.isServiceField;
    clone.resultField = this.resultField;
    clone.statusCodeField = this.statusCodeField;
    clone.responseTimeField = this.responseTimeField;
    int count = this.fieldName.length;
    clone.allocate( count );
    for ( int i = 0; i < count; i++ ) {
      clone.fieldName[ i ] = this.fieldName[ i ];
      clone.parameter[ i ] = this.parameter[ i ];
      clone.defaultValue[ i ] = this.defaultValue[ i ];
    }
    return clone;
  }

  public String getXML() {
    StringBuffer xml = new StringBuffer( 300 );
    xml.append( "    " ).append( XMLHandler.addTagValue( "server_url", this.serverURL ) );
    xml.append( "    " ).append( XMLHandler.addTagValue( "username", this.username ) );
    xml.append( "    " ).append( XMLHandler.addTagValue( "password", this.password ) );
    xml.append( "    " ).append( XMLHandler.addTagValue( "bypass_auth_check", this.bypassAuthCheck ) );
    xml.append( "    " ).append( XMLHandler.addTagValue( "module", this.module ) );
    xml.append( "    " ).append( XMLHandler.addTagValue( "is_module_field", this.isModuleField ) );
    xml.append( "    " ).append( XMLHandler.addTagValue( "service", this.service ) );
    xml.append( "    " ).append( XMLHandler.addTagValue( "is_service_field", this.isServiceField ) );
    xml.append( "    " ).append( XMLHandler.addTagValue( "result_field", this.resultField ) );
    xml.append( "    " ).append( XMLHandler.addTagValue( "status_code_field", this.statusCodeField ) );
    xml.append( "    " ).append( XMLHandler.addTagValue( "response_time_field", this.responseTimeField ) );
    xml.append( "    <fields>" ).append( Const.CR );
    for ( int i = 0; i < this.fieldName.length; i++ ) {
      xml.append( "      <field>" ).append( Const.CR );
      xml.append( "        " ).append( XMLHandler.addTagValue( "name", this.fieldName[ i ] ) );
      xml.append( "        " ).append( XMLHandler.addTagValue( "parameter", this.parameter[ i ] ) );
      xml.append( "        " ).append( XMLHandler.addTagValue( "default_value", this.defaultValue[ i ] ) );
      xml.append( "        </field>" ).append( Const.CR );
    }
    xml.append( "      </fields>" ).append( Const.CR );
    return xml.toString();
  }

  public void loadXML( Node stepNode, List<DatabaseMeta> databases, IMetaStore metaStore ) throws KettleXMLException {
    try {
      this.serverURL = XMLHandler.getTagValue( stepNode, "server_url" );
      if ( this.serverURL == null ) {
        this.serverURL = "";
      }
      this.username = XMLHandler.getTagValue( stepNode, "username" );
      if ( this.username == null ) {
        this.username = "";
      }
      this.password = XMLHandler.getTagValue( stepNode, "password" );
      if ( this.password == null ) {
        this.password = "";
      }
      this.bypassAuthCheck = "Y".equalsIgnoreCase( XMLHandler.getTagValue( stepNode, "bypass_auth_check" ) );
      this.module = XMLHandler.getTagValue( stepNode, "module" );
      if ( this.module == null ) {
        this.module = "";
      }
      this.isModuleField = "Y".equalsIgnoreCase( XMLHandler.getTagValue( stepNode, "is_module_field" ) );
      this.service = XMLHandler.getTagValue( stepNode, "service" );
      if ( this.service == null ) {
        this.service = "";
      }
      this.isServiceField = "Y".equalsIgnoreCase( XMLHandler.getTagValue( stepNode, "is_service_field" ) );
      this.resultField = XMLHandler.getTagValue( stepNode, "result_field" );
      if ( this.resultField == null ) {
        this.resultField = "";
      }
      this.statusCodeField = XMLHandler.getTagValue( stepNode, "status_code_field" );
      if ( this.statusCodeField == null ) {
        this.statusCodeField = "";
      }
      this.responseTimeField = XMLHandler.getTagValue( stepNode, "response_time_field" );
      if ( this.responseTimeField == null ) {
        this.responseTimeField = "";
      }
      Node fields = XMLHandler.getSubNode( stepNode, "fields" );
      int count = XMLHandler.countNodes( fields, "field" );
      allocate( count );
      for ( int i = 0; i < count; i++ ) {
        Node fieldNode = XMLHandler.getSubNodeByNr( fields, "field", i );
        this.fieldName[ i ] = XMLHandler.getTagValue( fieldNode, "name" );
        this.parameter[ i ] = XMLHandler.getTagValue( fieldNode, "parameter" );
        this.defaultValue[ i ] = XMLHandler.getTagValue( fieldNode, "default_value" );
      }
    } catch ( Exception e ) {
      throw new KettleXMLException(
        BaseMessages.getString( PKG, "BAServerUtils.RuntimeError.UnableToReadXML" ), e );
    }
  }

  public void readRep( Repository rep, IMetaStore metaStore, ObjectId id_step, List<DatabaseMeta> databases )
    throws KettleException {
    try {
      this.serverURL = rep.getStepAttributeString( id_step, "server_url" );
      this.username = rep.getStepAttributeString( id_step, "username" );
      this.password = rep.getStepAttributeString( id_step, "password" );
      this.bypassAuthCheck = rep.getStepAttributeBoolean( id_step, 0, "bypass_auth_check", false );
      this.module = rep.getStepAttributeString( id_step, "module" );
      this.isModuleField = rep.getStepAttributeBoolean( id_step, 0, "is_module_field", false );
      this.service = rep.getStepAttributeString( id_step, "service" );
      this.isServiceField = rep.getStepAttributeBoolean( id_step, 0, "is_service_field", false );
      this.resultField = rep.getStepAttributeString( id_step, "result_field" );
      this.statusCodeField = rep.getStepAttributeString( id_step, "status_code_field" );
      this.responseTimeField = rep.getStepAttributeString( id_step, "response_time_field" );
      int count = rep.countNrStepAttributes( id_step, "field_name" );
      allocate( count );
      for ( int i = 0; i < count; i++ ) {
        this.fieldName[ i ] = rep.getStepAttributeString( id_step, i, "field_name" );
        this.parameter[ i ] = rep.getStepAttributeString( id_step, i, "field_parameter" );
        this.defaultValue[ i ] = rep.getStepAttributeString( id_step, i, "field_default_value" );
      }
    } catch ( Exception e ) {
      throw new KettleException( BaseMessages.getString(
        PKG, "BAServerUtils.RuntimeError.UnableToReadRepository" ), e );
    }
  }

  public void saveRep( Repository rep, IMetaStore metaStore, ObjectId id_transformation, ObjectId id_step )
    throws KettleException {
    try {
      rep.saveStepAttribute( id_transformation, id_step, "server_url", this.serverURL );
      rep.saveStepAttribute( id_transformation, id_step, "username", this.username );
      rep.saveStepAttribute( id_transformation, id_step, "password", this.password );
      rep.saveStepAttribute( id_transformation, id_step, 0, "bypass_auth_check", this.bypassAuthCheck );
      rep.saveStepAttribute( id_transformation, id_step, "module", this.module );
      rep.saveStepAttribute( id_transformation, id_step, 0, "is_module_field", this.isModuleField );
      rep.saveStepAttribute( id_transformation, id_step, "service", this.service );
      rep.saveStepAttribute( id_transformation, id_step, 0, "is_service_field", this.isServiceField );
      rep.saveStepAttribute( id_transformation, id_step, "result_field", this.resultField );
      rep.saveStepAttribute( id_transformation, id_step, "status_code_field", this.statusCodeField );
      rep.saveStepAttribute( id_transformation, id_step, "response_time_field", this.responseTimeField );
      for ( int i = 0; i < fieldName.length; i++ ) {
        rep.saveStepAttribute( id_transformation, id_step, i, "field_name",
          Const.isEmpty( fieldName[ i ] ) ? "" : fieldName[ i ] );
        rep.saveStepAttribute( id_transformation, id_step, i, "field_parameter", parameter[ i ] );
        rep.saveStepAttribute( id_transformation, id_step, i, "field_default_value", defaultValue[ i ] );
      }
    } catch ( Exception e ) {
      throw new KettleException( BaseMessages.getString(
        PKG, "BAServerUtils.RuntimeError.UnableToSaveRepository", "" + id_step ), e );
    }
  }

  public void check( List<CheckResultInterface> remarks, TransMeta transMeta, StepMeta stepMeta,
                     RowMetaInterface prev, String[] input, String[] output, RowMetaInterface info, VariableSpace space,
                     Repository repository, IMetaStore metaStore ) {

    // see if we have fields from previous steps
    if ( prev == null || prev.size() == 0 ) {
      remarks.add( new CheckResult( CheckResultInterface.TYPE_RESULT_WARNING,
        BaseMessages.getString( PKG, "BAServerUtils.CheckResult.NotReceivingFieldsFromPreviousSteps" ),
        stepMeta ) );
    } else {
      remarks.add( new CheckResult( CheckResultInterface.TYPE_RESULT_OK, BaseMessages
        .getString( PKG, "BAServerUtils.CheckResult.ReceivingFieldsFromPreviousSteps", "" + prev.size() ),
        stepMeta ) );
    }

    // see if we have input streams leading to this step
    if ( input.length > 0 ) {
      remarks.add( new CheckResult( CheckResultInterface.TYPE_RESULT_OK,
        BaseMessages.getString( PKG, "BAServerUtils.CheckResult.ReceivingInfoFromOtherSteps" ), stepMeta ) );
    } else {
      remarks.add( new CheckResult( CheckResultInterface.TYPE_RESULT_ERROR,
        BaseMessages.getString( PKG, "BAServerUtils.CheckResult.NotReceivingInfoFromOtherSteps" ),
        stepMeta ) );
    }
  }

  @Override
  public void getFields( RowMetaInterface inputRowMeta, String name, RowMetaInterface[] info, StepMeta nextStep,
                         VariableSpace space, Repository repository, IMetaStore metaStore ) throws KettleStepException {
    ValueMetaInterface vmi;

    if ( !Const.isEmpty( this.resultField ) ) {
      vmi = new ValueMeta( space.environmentSubstitute( this.resultField ), ValueMeta.TYPE_STRING );
      vmi.setOrigin( name );
      inputRowMeta.addValueMeta( vmi );
    }

    if ( !Const.isEmpty( this.statusCodeField ) ) {
      vmi = new ValueMeta( space.environmentSubstitute( this.statusCodeField ), ValueMeta.TYPE_INTEGER );
      vmi.setOrigin( name );
      inputRowMeta.addValueMeta( vmi );
    }

    if ( !Const.isEmpty( this.responseTimeField ) ) {
      vmi = new ValueMeta( space.environmentSubstitute( this.responseTimeField ), ValueMeta.TYPE_INTEGER );
      vmi.setOrigin( name );
      inputRowMeta.addValueMeta( vmi );
    }
  }
}
