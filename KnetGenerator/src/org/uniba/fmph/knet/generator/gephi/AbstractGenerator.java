/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator.gephi;

import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.project.api.ProjectController;
import org.gephi.utils.progress.Progress;
import org.gephi.utils.progress.ProgressTicket;
import org.openide.util.Lookup;
import org.uniba.fmph.knet.generator.IGenerator;
import org.uniba.fmph.knet.generator.ProgressIndicator;
import org.uniba.fmph.knet.generator.graph.Graph;

/**
 *
 * @author pna
 */
public abstract class AbstractGenerator<G extends IGenerator<NodeDraft>> implements Generator {
  
    private static final String COLUMN_TIME = "Time";
    private ProgressTicket progressTicket;   
    private G generator;
    protected abstract int workUnits();

    public AbstractGenerator() {
        generator = createGenerator();
    }
    
    
    protected Graph<NodeDraft> setupGraph(ContainerLoader container){
        //Start progress
        
        //Add the time interval column if not exist
        AttributeModel attributeModel = container.getAttributeModel();
        AttributeColumn col;              
        if ((col = attributeModel.getNodeTable().getColumn(COLUMN_TIME)) == null) {
            col = attributeModel.getNodeTable().addColumn(COLUMN_TIME, AttributeType.INT);
        }
        container.setEdgeDefault(EdgeDefault.UNDIRECTED);
        return new ContainerLoaderGraph(container, col);
    }
    
    protected abstract G createGenerator();
    
    protected G getGenerator(){
        return generator;
    }
        
    
   
    
    @Override
    public void generate(ContainerLoader container) {       
        Graph<NodeDraft> graph = setupGraph(container);
        Progress.start(getProgressTicket(),workUnits());        
        getGenerator().setProgressIndicator(new ProgressIndicator() {
            @Override
            public void indicateProgress() {
               getProgressTicket().progress();
            }
        });
        getGenerator().generate(graph);
        Progress.finish(getProgressTicket());
    }

   
    @Override
    public boolean cancel() {
        return getGenerator().cancel();        
    }

    @Override
    public void setProgressTicket(ProgressTicket pt) {
        this.progressTicket = pt;
    }

    /**
     * @return the progressTicket
     */
    public ProgressTicket getProgressTicket() {
        return progressTicket;
    }


}